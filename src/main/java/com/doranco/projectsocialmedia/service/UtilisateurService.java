/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.doranco.projectsocialmedia.service;

import com.doranco.projectsocialmedia.dto.UtilisateurDto;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import java.util.Map;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Assia
 */
public interface UtilisateurService {

    UtilisateurDto findbyId(Long id);

    Utilisateur findByEmail(String email);

    public ResponseEntity<Map<String, Object>> lister(int page, int size);

    ResponseEntity<?> saveUtilisateur(Utilisateur utilisateur);

    void updateUtilisateur(Long id, Utilisateur utilisateur);

    void deleteById(Long id);

    public void activateUtilisateur(Long id);

    public void desactivateUtilisateur(Long id);
    
    public UtilisateurDto convertEntityToDto(Utilisateur utilisateur);

}
