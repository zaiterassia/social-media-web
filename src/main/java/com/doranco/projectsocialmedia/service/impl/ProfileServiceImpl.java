/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.service.impl;

import com.doranco.projectsocialmedia.dto.UtilisateurDto;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import com.doranco.projectsocialmedia.repository.UtilisateurRepository;
import com.doranco.projectsocialmedia.service.CurrentUserService;
import com.doranco.projectsocialmedia.service.FileUploadService;
import com.doranco.projectsocialmedia.service.ProfileService;
import com.doranco.projectsocialmedia.service.RegisterService;
import com.doranco.projectsocialmedia.service.UtilisateurService;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Assia
 */
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegisterService registerService;
    private final UtilisateurService utilisateurService;
    private final CurrentUserService currentUserService;
    private final FileUploadService uploadService;
    private final String uploadDirectory = "src/main/resources/static/images/users-avatar";
    private final String url = "images/users-avatar";

    @Override
    public UtilisateurDto getCurrentUser() {
        return utilisateurService.convertEntityToDto(currentUserService.getCurrentUser());
    }

    @Override
    public void updateAvatar(MultipartFile avatar) throws IOException {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        String path = uploadDirectory + File.separator + currentUser.getId();
        String url = this.url + File.separator + currentUser.getId();
        String filename = StringUtils.cleanPath(Objects.requireNonNull(avatar.getOriginalFilename()));
        registerService.uploadAvatar(avatar, path, filename);
        currentUser.setAvatar(url + File.separator + filename);
        utilisateurRepository.save(currentUser);
    }

    @Override
    public void updateProfile(Utilisateur utilisateur) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur utilisateurAmodifier = utilisateurRepository.findByEmail(authentication.getName());
        utilisateurAmodifier.setNom(utilisateur.getNom());
        utilisateurAmodifier.setPrenom(utilisateur.getPrenom());
        utilisateurAmodifier.setEmail(utilisateur.getEmail());
        utilisateurAmodifier.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        utilisateurAmodifier.setRole(utilisateur.getRole());
        utilisateurAmodifier.setSexe(utilisateur.getSexe());
        utilisateurAmodifier.setTelephone(utilisateur.getTelephone());
        utilisateurAmodifier.setAvatar(utilisateur.getAvatar());
        utilisateurAmodifier.setDescription(utilisateur.getDescription());

        utilisateurRepository.save(utilisateurAmodifier);
    }
    
    @Override
    public void registerUtilisateur(Utilisateur utilisateur, MultipartFile avatar) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur utilisateurAmodifier = utilisateurRepository.findByEmail(authentication.getName());
        utilisateurAmodifier.setNom(utilisateur.getNom());
        utilisateurAmodifier.setPrenom(utilisateur.getPrenom());
        utilisateurAmodifier.setEmail(utilisateur.getEmail());
        utilisateurAmodifier.setRole(utilisateur.getRole());
        utilisateurAmodifier.setSexe(utilisateur.getSexe());
        utilisateurAmodifier.setTelephone(utilisateur.getTelephone());
        utilisateurAmodifier.setDescription(utilisateur.getDescription());
        if (avatar != null) {
            String filename = StringUtils.cleanPath(Objects.requireNonNull(avatar.getOriginalFilename()));
            try {
                String path = uploadDirectory + File.separator + utilisateur.getId();
                String url = this.url + File.separator + utilisateur.getId();
                uploadAvatar(avatar, path, filename);
                utilisateurAmodifier.setAvatar(url + File.separator + filename);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        utilisateurRepository.save(utilisateurAmodifier);
    }
    
        public void uploadAvatar(MultipartFile avatarFile, String path, String filename) throws IOException {

        uploadService.saveFile(path, filename, avatarFile);
    }

}
