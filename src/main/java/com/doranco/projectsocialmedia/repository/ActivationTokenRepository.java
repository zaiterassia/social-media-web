/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.repository;

import com.doranco.projectsocialmedia.entity.ActivationToken;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Assia
 */
public interface ActivationTokenRepository extends JpaRepository<ActivationToken, Long> {
    ActivationToken findByAccount(Utilisateur account);
    
}
