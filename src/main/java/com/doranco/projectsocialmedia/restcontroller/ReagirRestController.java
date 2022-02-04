/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.restcontroller;

import com.doranco.projectsocialmedia.dto.ReagirDto;
import com.doranco.projectsocialmedia.service.ReagirService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Assia
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/reagir")
public class ReagirRestController {

    private final ReagirService reagirService;

    @GetMapping("/mesreaction")
    public ResponseEntity<Map<String, Object>> getListReagir(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return reagirService.findAllByCurrentUtilisateur(page, size);
    }

    @GetMapping("/{id}")
    public ReagirDto getReagirById(@PathVariable(value = "id") Long idReagir) {
        return reagirService.findbyId(idReagir);

    }

    @PostMapping("/like/{idPublication}")
    public ResponseEntity<?> saveLikeReagir(@PathVariable(value = "idPublication") Long idPublication) {
        return reagirService.saveReagir(idPublication, true);
    }

    @PostMapping("/dislike/{idPublication}")
    public ResponseEntity<?> saveDislikeReagir(@PathVariable(value = "idPublication") Long idPublication) {
        return reagirService.saveReagir(idPublication, false);
    }

    @PutMapping("/like/{idPublication}")
    public ResponseEntity<?> updateLikeReagir(@PathVariable(value = "idPublication") Long idPublication) {
        return reagirService.updateReagir(idPublication, true);
    }

    @PutMapping("/dislike/{idPublication}")
    public ResponseEntity<?> updateDislikeReagir(@PathVariable(value = "idPublication") Long idPublication) {
        return reagirService.updateReagir(idPublication, false);
    }

    @DeleteMapping("/{idPublication}")
    public void deleteReagir(@PathVariable(value = "idPublication") Long idPublication) {
        reagirService.deleteReagir(idPublication);
    }

}
