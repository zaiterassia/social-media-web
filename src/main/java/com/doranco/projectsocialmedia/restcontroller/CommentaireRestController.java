/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.restcontroller;

import com.doranco.projectsocialmedia.dto.CommentaireDto;
import com.doranco.projectsocialmedia.entity.Commentaire;
import com.doranco.projectsocialmedia.service.CommentaireService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Assia
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/commentaire")
public class CommentaireRestController {

    private final CommentaireService commentaireService;

    @GetMapping("/mescommentaires")
    public ResponseEntity<Map<String, Object>> getListCommentaire(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return commentaireService.findAllByCurrentUtilisateur(page, size);
    }

    @GetMapping("/{id}")
    public CommentaireDto getCommentaireById(@PathVariable(value = "id") Long idCommentaire) {
        return commentaireService.findbyId(idCommentaire);

    }

    @PostMapping("/{idPublication}")
    public ResponseEntity<?> saveCommentaire(
            @RequestBody Commentaire commentaire,
            @PathVariable(value = "idPublication") Long idPublication) {
        return commentaireService.saveCommentaire(idPublication, commentaire);
    }

    @PutMapping("/{idPublication}")
    public ResponseEntity<?> updateCommentaire(
            @RequestBody Commentaire commenatire,
            @PathVariable(value = "idPublication") Long idPublication) {
        return commentaireService.updateCommentaire(idPublication, commenatire);
    }

    @DeleteMapping("/{idPublication}")
    public void deleteCommentaire(@PathVariable(value = "idPublication") Long idPublication) {
        commentaireService.deleteCommentaire(idPublication);
    }

}
