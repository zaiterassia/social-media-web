/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.doranco.projectsocialmedia.service;

import com.doranco.projectsocialmedia.dto.PublicationsDto;
import com.doranco.projectsocialmedia.entity.Publication;
import com.doranco.projectsocialmedia.entity.PublicationEpreuve;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Assia
 */
public interface PublicationService {

    PublicationsDto findbyId(Long id);

    ResponseEntity<Map<String, Object>> lister(int page, int size);

    ResponseEntity<?> savePublication(Publication publication, MultipartFile media);

    ResponseEntity<?> saveEpreuvePublication(PublicationEpreuve publication);

    void updatePublication(Long id, Publication publication, MultipartFile media);

    void deleteById(Long id);

    ResponseEntity<Map<String, Object>> findAllByCurrentUtilisateur(int page, int size);

    ResponseEntity<Map<String, Object>> findAllByUtilisateur(Long idUtilisateur, int page, int size);

    //ResponseEntity<Map<String, Object>> findAllByUtilisateurByVisibilite(Long idUtilisateur, Visibilite visibilite, int page, int size);
}
