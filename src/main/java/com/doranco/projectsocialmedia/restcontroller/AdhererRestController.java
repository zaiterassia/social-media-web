/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.restcontroller;

import com.doranco.projectsocialmedia.dto.AdhererDto;
import com.doranco.projectsocialmedia.service.AdhererService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Assia
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/adherer")
public class AdhererRestController {

    private final AdhererService adhererService;

    @GetMapping("/mesadhesions")
    public ResponseEntity<Map<String, Object>> getListAdherer(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return adhererService.findAllByCurrentUtilisateur(page, size);
    }

    @GetMapping("/mesgroupes")
    public ResponseEntity<Map<String, Object>> getMyGroupeList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return adhererService.listerGroupe(page, size);
    }

    @GetMapping("/{id}")
    public AdhererDto getAdhererById(@PathVariable(value = "id") Long idAdherer) {
        return adhererService.findbyId(idAdherer);

    }

    @PostMapping("/{idGroupe}")
    public ResponseEntity<?> saveAdherer(@PathVariable(value = "idGroupe") Long idGroupe) {
        return adhererService.saveAdhesion(idGroupe);
    }

    @DeleteMapping("/{idGroupe}")
    public void deleteAdherer(@PathVariable(value = "idGroupe") Long idGroupe) {
        adhererService.deleteAdhesion(idGroupe);
    }

}
