/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.doranco.projectsocialmedia.repository;

import com.doranco.projectsocialmedia.entity.EnvoyerMessage;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Assia
 */
public interface EnvoyerMessageRepository extends JpaRepository<EnvoyerMessage, Long> {

    Page<EnvoyerMessage> findAllByEmmetteur(Utilisateur utilisateur, Pageable page);

    public Page<EnvoyerMessage> findAllByEmmetteurAndRecepteur(Utilisateur currentUser, Utilisateur destinataire, Pageable paging);

    public Page<EnvoyerMessage> findAllByRecepteur(Utilisateur currentUser, Pageable paging);
    
    @Query("SELECT u FROM EnvoyerMessage u WHERE (u.recepteur = :currentUser and u.emmetteur = :destUser) Or (u.recepteur = :destUser and u.emmetteur = :currentUser) ORDER BY date_envoie ASC")
    public Page<EnvoyerMessage> findAllByUtilisateur(Utilisateur currentUser, Utilisateur destUser, Pageable paging);

    public Page<EnvoyerMessage> findAllByEmmetteurOrRecepteur(Utilisateur currentUser, Utilisateur utilisateur, Pageable paging);

}
