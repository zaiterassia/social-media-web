/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.restcontroller;

import com.doranco.projectsocialmedia.dto.EnvoyerDemandeDto;
import com.doranco.projectsocialmedia.entity.EnvoyerDemande;
import com.doranco.projectsocialmedia.entity.Etat;
import com.doranco.projectsocialmedia.service.EnvoyerDemandeService;
import java.util.List;
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
@RequestMapping("api/demandes")
public class EnvoyerDemandeRestController {

    private final EnvoyerDemandeService EnvoyerDemandeService;
    
    @GetMapping("/accepted")
    public List<EnvoyerDemande> getAllAcceptedDemande() {
        return EnvoyerDemandeService.getAcceptedDemande();
    }
    
    @GetMapping("/friends")
    public ResponseEntity<Map<String, Object>> getAllFreinds(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        return EnvoyerDemandeService.findAllFreinds(page, size);
    }

    @GetMapping("/envoye")
    public ResponseEntity<Map<String, Object>> getListEnvoyerDemande(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return EnvoyerDemandeService.findAllEnvoyeDemande(page, size);
    }

    @GetMapping("/recu")
    public ResponseEntity<Map<String, Object>> getListRecuDemande(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return EnvoyerDemandeService.findAllRecuDemande(page, size);
    }

    @GetMapping("/{id}")
    public EnvoyerDemandeDto getEnvoyerDemandeById(@PathVariable(value = "id") Long idEnvoyerDemande) {
        return EnvoyerDemandeService.findbyId(idEnvoyerDemande);

    }
    
    @PostMapping("/{idUtilisateur}")
    public ResponseEntity<?> saveEnvoyerDemande(@PathVariable(value = "idUtilisateur") Long idUtlisateur) {
        return EnvoyerDemandeService.saveEnvoyerDemande(idUtlisateur);
    }

    @PostMapping("/accpet/{idDemande}")
    public ResponseEntity<?> saveaccpetEnvoyerDemande(@PathVariable(value = "idDemande") Long idDemande) {
        return EnvoyerDemandeService.saveEnvoyerDemande(idDemande, Etat.ACCEPTER);
    }

    @PostMapping("/refuser/{idDemande}")
    public ResponseEntity<?> saveDislikeEnvoyerDemande(@PathVariable(value = "idDemande") Long idDemande) {
        return EnvoyerDemandeService.saveEnvoyerDemande(idDemande, Etat.REFUSER);
    }

    @PutMapping("/accpet/{idDemande}")
    public ResponseEntity<?> updateLikeEnvoyerDemande(@PathVariable(value = "idDemande") Long idDemande) {
        return EnvoyerDemandeService.updateEnvoyerDemande(idDemande, Etat.ACCEPTER);
    }

    @PutMapping("/refuser/{idDemande}")
    public ResponseEntity<?> updateDislikeEnvoyerDemande(@PathVariable(value = "idDemande") Long idDemande) {
        return EnvoyerDemandeService.updateEnvoyerDemande(idDemande, Etat.REFUSER);
    }

    @DeleteMapping("/{idDemande}")
    public void deleteEnvoyerDemande(@PathVariable(value = "idDemande") Long idDemande) {
        EnvoyerDemandeService.deleteEnvoyerDemande(idDemande);
    }

}
