/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.service.impl;

import com.doranco.projectsocialmedia.entity.ActivationToken;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import com.doranco.projectsocialmedia.repository.ActivationTokenRepository;
import com.doranco.projectsocialmedia.repository.UtilisateurRepository;
import com.doranco.projectsocialmedia.service.FileUploadService;
import com.doranco.projectsocialmedia.service.RegisterService;
import com.doranco.projectsocialmedia.service.UtilisateurService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Assia
 */
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UtilisateurRepository utilisateurRepository;
    private final ActivationTokenRepository activationTokenRepository;
    private final UtilisateurService utilisateurService;
    private final PasswordEncoder passwordEncoder;
    private final FileUploadService uploadService;
    private final String uploadDirectory = "src/main/resources/static/images/users-avatar";
    private final String url = "images/users-avatar";
    @Autowired
    public JavaMailSender emailSender;

    @Override
    public ResponseEntity<?> registerUtilisateur(Utilisateur utilisateur, MultipartFile avatar) {
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        utilisateur.setActif(false);
        ResponseEntity<?> response;
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        response = ResponseEntity
                .status(HttpStatus.CREATED).body(utilisateurService.convertEntityToDto(savedUtilisateur));
        // upload avatar if not null
        if (avatar != null) {
            String filename = StringUtils.cleanPath(Objects.requireNonNull(avatar.getOriginalFilename()));
            try {
                String path = uploadDirectory + File.separator + utilisateur.getId();
                String url = this.url + File.separator + utilisateur.getId();
                uploadAvatar(avatar, path, filename);
                savedUtilisateur.setAvatar(url + File.separator + filename);
                response = ResponseEntity
                .status(HttpStatus.CREATED).body(utilisateurService.convertEntityToDto(utilisateurRepository.save(savedUtilisateur)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }      
        //mail activation

        if (response != null) {
            try {
                this.sendActivationMail(utilisateur);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return response;
    }

    @Override
    public ResponseEntity<?> activateUtilisateur(String email, String token) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
        ActivationToken activationToken = activationTokenRepository.findByAccount(utilisateur);
        if (activationToken != null && activationToken.getToken().equals(token)) {
            utilisateur.setActif(true);
            utilisateurRepository.save(utilisateur);
            activationTokenRepository.delete(activationToken);
            return ResponseEntity
                    .status(HttpStatus.OK).body("votre compte est activé avec succès");
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).body("compte non trouvé ou token invalide");
        }
    }

    @Override
    public ResponseEntity<?> desactivateUtilisateur() {
        Utilisateur currentUser = utilisateurRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        currentUser.setActif(false);
        utilisateurRepository.save(currentUser);
        return ResponseEntity
                .status(HttpStatus.OK).body("votre compte est desactivé avec succès");
    }

    private void sendActivationMail(Utilisateur utilisateur) throws MessagingException, FileNotFoundException, IOException {
        String token = UUID.randomUUID().toString();
        ActivationToken activationToken = new ActivationToken(token, utilisateur, new Date());
        activationTokenRepository.save(activationToken);

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
        File file = ResourceUtils.getFile("classpath:templates/mailActivationModel.html");

        InputStream inputStream = new FileInputStream(file);
        String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        inputStream.close();
        String name = utilisateur.getNom() + " " + utilisateur.getPrenom();
        String link = "http://localhost:8080/api/register/activate?email=" + utilisateur.getEmail() + "&token=" + token;

        content = content.replace("{{name}}", name);
        content = content.replace("{{link}}", link);
        message.setContent(content, "text/html");

        helper.setTo(utilisateur.getEmail());

        helper.setSubject("Activation de votre compte");

        this.emailSender.send(message);
    }

    @Override
    public void uploadAvatar(MultipartFile avatarFile, String path, String filename) throws IOException {

        uploadService.saveFile(path, filename, avatarFile);
    }

}
