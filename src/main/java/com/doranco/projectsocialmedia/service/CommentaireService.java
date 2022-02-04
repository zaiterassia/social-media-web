/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.service;

import com.doranco.projectsocialmedia.dto.CommentaireDto;
import com.doranco.projectsocialmedia.entity.Commentaire;
import java.util.Map;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Assia
 */
public interface CommentaireService {

    public ResponseEntity<Map<String, Object>> findAllByCurrentUtilisateur(int page, int size);

    public CommentaireDto findbyId(Long idCommentaire);

    public ResponseEntity<?> updateCommentaire(Long idCommentaire, Commentaire commentaire);

    public ResponseEntity<?> saveCommentaire(Long idPublication, Commentaire commentaire);

    public void deleteCommentaire(Long idPublication);

    public ResponseEntity<Map<String, Object>> findAllByLikePublication(Long idPublication, int page, int size);
    
}
