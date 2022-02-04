/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.doranco.projectsocialmedia.service;

import com.doranco.projectsocialmedia.entity.Utilisateur;
import java.io.IOException;
import javax.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Assia
 */
public interface RegisterService {

    ResponseEntity<?> registerUtilisateur(Utilisateur utilisateur, MultipartFile avatarFile) throws MessagingException;

    ResponseEntity<?> activateUtilisateur(String email, String token);

    ResponseEntity<?> desactivateUtilisateur();

    void uploadAvatar(MultipartFile avatarFile, String path, String filename) throws IOException;

}
