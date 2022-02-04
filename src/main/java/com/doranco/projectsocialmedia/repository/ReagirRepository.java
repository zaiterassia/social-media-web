/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.repository;

import com.doranco.projectsocialmedia.entity.Reagir;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Assia
 */
public interface ReagirRepository  extends JpaRepository<Reagir, Long> {
    
    Page<Reagir> findAllByLikeUser(Utilisateur utilisateur, Pageable page);
    Page<Reagir> findAllByLikePublication_id(Long idPublication, Pageable page);
    Reagir findByLikeUserAndLikePublication_id(Utilisateur utilisateur, Long idPublication);
    
}
