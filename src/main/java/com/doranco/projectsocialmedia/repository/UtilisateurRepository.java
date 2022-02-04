/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.doranco.projectsocialmedia.repository;

import com.doranco.projectsocialmedia.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Assia
 */
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByEmail(String email);
    
}
