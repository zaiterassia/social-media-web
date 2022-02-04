/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.service.impl;

import com.doranco.projectsocialmedia.dto.CommentaireDto;
import com.doranco.projectsocialmedia.entity.Publication;
import com.doranco.projectsocialmedia.entity.Commentaire;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import com.doranco.projectsocialmedia.repository.PublicationRepository;
import com.doranco.projectsocialmedia.repository.CommentaireRepository;
import com.doranco.projectsocialmedia.service.CommentaireService;
import com.doranco.projectsocialmedia.service.CurrentUserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author Assia
 */
@Service
@RequiredArgsConstructor
public class CommentaireServiceImpl implements CommentaireService {

    private final CommentaireRepository commentaireRepository;
    private final PublicationRepository publicationRepository;
    private final CurrentUserService currentUserService;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> saveCommentaire(Long idPublication, Commentaire commentaire) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        Publication publication = publicationRepository.findById(idPublication).orElse(null);
        if (publication != null) {
            commentaire.setCommentaireUser(currentUser);
            commentaire.setCommentairePublication(publication);

            return ResponseEntity
                    .status(HttpStatus.CREATED).body(this.convertEntityToDto(commentaireRepository.save(commentaire)));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).body("publication non trouvé");
        }
    }

    @Override
    public CommentaireDto findbyId(Long idCommentaire) {
        return this.convertEntityToDto(commentaireRepository.findById(idCommentaire).orElse(null));
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllByCurrentUtilisateur(int page, int size) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        List<CommentaireDto> commentaires;
        Pageable paging = PageRequest.of(page, size);
        Page<Commentaire> pageCommentaires = commentaireRepository.findAllByCommentaireUser(currentUser, paging);
        commentaires = pageCommentaires.getContent()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        if (commentaires.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("commentaire", commentaires);
        response.put("currentPage", pageCommentaires.getNumber());
        response.put("totalItems", pageCommentaires.getTotalElements());
        response.put("totalPages", pageCommentaires.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllByLikePublication(Long idPublication, int page, int size) {
        List<CommentaireDto> commentaires;
        Pageable paging = PageRequest.of(page, size);
        Page<Commentaire> pageCommentaires = commentaireRepository.findAllByCommentairePublication_id(idPublication, paging);
        commentaires = pageCommentaires.getContent()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        if (commentaires.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("commentaire", commentaires);
        response.put("currentPage", pageCommentaires.getNumber());
        response.put("totalItems", pageCommentaires.getTotalElements());
        response.put("totalPages", pageCommentaires.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateCommentaire(Long idPublication, Commentaire commentaire) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        Commentaire foundCommentaire = commentaireRepository.findByCommentaireUserAndCommentairePublication_id(currentUser, idPublication);
        if (commentaire != null) {
            commentaire.setTexte(commentaire.getTexte());

            return ResponseEntity
                    .status(HttpStatus.OK).body(this.convertEntityToDto(commentaireRepository.save(foundCommentaire)));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).body("publication non trouvé");
        }
    }

    @Override
    public void deleteCommentaire(Long idCommentaire) {
        commentaireRepository.deleteById(idCommentaire);
    }

    private CommentaireDto convertEntityToDto(Commentaire commentaire) {
        return modelMapper.map(commentaire, CommentaireDto.class
        );

    }

}
