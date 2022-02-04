/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.restcontroller;

import com.doranco.projectsocialmedia.dto.GroupeDto;
import com.doranco.projectsocialmedia.entity.Groupe;
import com.doranco.projectsocialmedia.service.AdhererService;
import com.doranco.projectsocialmedia.service.GroupeService;
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
@RequestMapping("api/groupe")
public class GroupeRestController {

    private final GroupeService groupeService;
    private final AdhererService adhererService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getListGroupe(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return groupeService.lister(page, size);
    }

    @GetMapping("/mesgroupes")
    public ResponseEntity<Map<String, Object>> getMyGroupeList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return groupeService.findAllByCurrentUtilisateur(page, size);
    }

    @GetMapping("/{id}")
    public GroupeDto getGroupeById(@PathVariable(value = "id") Long idGroupe) {
        return groupeService.findbyId(idGroupe);

    }

    @GetMapping("/{id}/membres")
    public ResponseEntity<Map<String, Object>> getGroupeMembres(
            @PathVariable(value = "id") Long idGroupe,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        return adhererService.listerMembres(idGroupe, page, size);

    }

    @PostMapping
    public ResponseEntity<?> saveGroupe(@RequestBody Groupe groupe) {
        return groupeService.saveGroupe(groupe);
    }

    @PutMapping("/{id}")
    public void updateGroupe(@RequestBody Groupe groupe, @PathVariable(value = "id") Long idGroupe) {
        groupeService.updateGroupe(idGroupe, groupe);

    }

    @DeleteMapping("/{id}")
    public void deleteGroupe(@PathVariable(value = "id") Long idGroupe) {
        groupeService.deleteById(idGroupe);
    }

    @PostMapping("/activate/{id}")
    public void activateGroupe(@PathVariable(value = "id") Long idGroupe) {
        groupeService.activateGroupe(idGroupe);

    }

    @PostMapping("/desactivate/{id}")
    public void desactivateGroupe(@PathVariable(value = "id") Long idGroupe) {
        groupeService.desactivateGroupe(idGroupe);

    }
}
