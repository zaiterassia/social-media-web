/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.doranco.projectsocialmedia.service;

import com.doranco.projectsocialmedia.dto.UtilisateurDto;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Assia
 */
public interface ProfileService {

    UtilisateurDto getCurrentUser();

    public void updateAvatar(MultipartFile avatar) throws IOException;
    
     public void updateProfile(Utilisateur utilisateur);
     
     public void registerUtilisateur(Utilisateur utilisateur, MultipartFile avatar);

}
