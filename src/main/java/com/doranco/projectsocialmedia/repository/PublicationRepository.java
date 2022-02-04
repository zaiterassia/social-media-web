/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.doranco.projectsocialmedia.repository;

import com.doranco.projectsocialmedia.entity.Publication;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Assia
 */
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    List<Publication> findAllByPublisherOrderByDateCreationDesc(Utilisateur utilisateur);

    Page<Publication> findAllByPublisher_id(Long idPublisher, Pageable page);

//    Page<Publication> findAllByPublisher_idByVisibiliteIs(Long idUtilisateur, Visibilite visibilite, Pageable paging);

}
