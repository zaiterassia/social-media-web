/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.service;

import com.doranco.projectsocialmedia.entity.Utilisateur;
import com.doranco.projectsocialmedia.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Assia
 */
@Component
@RequiredArgsConstructor
public class CurrentUserService {
    
    private final UtilisateurRepository utilisateurRepository;
    
    public Utilisateur getCurrentUser () {
        return utilisateurRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        
    }
    
}
