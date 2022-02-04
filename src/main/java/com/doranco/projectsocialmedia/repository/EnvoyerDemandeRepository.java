/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.repository;

import com.doranco.projectsocialmedia.entity.EnvoyerDemande;
import com.doranco.projectsocialmedia.entity.Etat;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Assia
 */
public interface EnvoyerDemandeRepository extends JpaRepository<EnvoyerDemande, Long> {

    Page<EnvoyerDemande> findAllByDemandeur(Utilisateur utilisateur, Pageable page);

    public Page<EnvoyerDemande> findAllByDestinataire(Utilisateur currentUser, Pageable paging);
    
    @Query("SELECT u FROM EnvoyerDemande u WHERE (u.demandeur = :currentUser Or u.destinataire = :currentUser) AND etat = :state ORDER BY date_envoi DESC")
    
    public List<EnvoyerDemande> findAllAccepted(Utilisateur currentUser, Etat state);

}
