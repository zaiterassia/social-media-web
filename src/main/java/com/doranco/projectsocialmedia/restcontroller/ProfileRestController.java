/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.restcontroller;

import com.doranco.projectsocialmedia.dto.UtilisateurDto;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import com.doranco.projectsocialmedia.service.ProfileService;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Assia
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/profile")
public class ProfileRestController {
    
    private final ProfileService profileService;

    
    @GetMapping
    public UtilisateurDto getCurrentUser() {
        return profileService.getCurrentUser();


    }
    
    @PutMapping("/update-avatar")
    public void uploadAvatar(@RequestParam(value = "avatar", required = false) MultipartFile avatar) throws IOException{
        profileService.updateAvatar(avatar);

    } 



    @PutMapping
    public void updateCurrentUtilisateur(@RequestPart("user") Utilisateur utilisateur, @RequestPart(value = "avatar", required = false) MultipartFile avatar) {
        profileService.registerUtilisateur(utilisateur, avatar);

    }

}
