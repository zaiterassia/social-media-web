/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.restcontroller;

import com.doranco.projectsocialmedia.dto.UtilisateurDto;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import com.doranco.projectsocialmedia.service.UtilisateurService;
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
@RequestMapping("api/utilisateur")
public class UtilisateurRestController {

    private final UtilisateurService utilisateurService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getListUtilisateur(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return utilisateurService.lister(page, size);

    }

    @GetMapping("/{id}")
    public UtilisateurDto getUtilisateurById(@PathVariable(value = "id") Long idUtilisateur) {
        return utilisateurService.findbyId(idUtilisateur);

    }

    @PostMapping
    public ResponseEntity<?> saveUtilisateur(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.saveUtilisateur(utilisateur);
    }

    @PutMapping("/{id}")
    public void updateUtilisateur(@RequestBody Utilisateur utilisateur, @PathVariable(value = "id") Long idUtilisateur) {
        utilisateurService.updateUtilisateur(idUtilisateur, utilisateur);
    }

    @DeleteMapping("/{id}")
    public void deleteUtilisateur(@PathVariable(value = "id") Long idUtilisateur) {
        utilisateurService.deleteById(idUtilisateur);
    }

    @PostMapping("activate/{id}")
    public void activateUtilisateur(@PathVariable(value = "id") Long idUtilisateur) {
        utilisateurService.activateUtilisateur(idUtilisateur);

    }

    @PostMapping("desactivate/{id}")
    public void desactivateUtilisateur(@PathVariable(value = "id") Long idUtilisateur) {
        utilisateurService.desactivateUtilisateur(idUtilisateur);

    }
}
