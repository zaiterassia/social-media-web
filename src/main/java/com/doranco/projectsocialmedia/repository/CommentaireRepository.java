/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.repository;

import com.doranco.projectsocialmedia.entity.Commentaire;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Assia
 */
public interface CommentaireRepository extends JpaRepository<Commentaire, Long>{
    Page<Commentaire> findAllByCommentaireUser(Utilisateur utilisateur, Pageable page);
    Page<Commentaire> findAllByCommentairePublication_id(Long idPublication, Pageable page);
    Commentaire findByCommentaireUserAndCommentairePublication_id(Utilisateur utilisateur, Long idPublication);
    
}
