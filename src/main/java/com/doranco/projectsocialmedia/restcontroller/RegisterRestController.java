/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.restcontroller;

import com.doranco.projectsocialmedia.entity.Utilisateur;
import com.doranco.projectsocialmedia.service.RegisterService;
import javax.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("api/register")
public class RegisterRestController {

    private final RegisterService registerService;

    @PostMapping(consumes = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> registerUtilisateur(@RequestPart("user") Utilisateur utilisateur, @RequestPart(value = "avatar", required = false) MultipartFile avatar) throws MessagingException {

        return registerService.registerUtilisateur(utilisateur, avatar);

    }

    @GetMapping("/activate")
    public ResponseEntity<?> activateUtilisateur(
            @RequestParam("email") String email,
            @RequestParam("token") String token
    ) {
        return registerService.activateUtilisateur(email, token);
    }

    @GetMapping("/desactivate")
    public ResponseEntity<?> desactivateUtilisateur() {
        return registerService.desactivateUtilisateur();
    }

}
