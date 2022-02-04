/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.service;

import com.doranco.projectsocialmedia.dto.EnvoyerDemandeDto;
import com.doranco.projectsocialmedia.entity.EnvoyerDemande;
import com.doranco.projectsocialmedia.entity.Etat;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;


/**
 *
 * @author Assia
 */
public interface EnvoyerDemandeService {

    public ResponseEntity<Map<String, Object>> findAllEnvoyeDemande(int page, int size);

    public ResponseEntity<Map<String, Object>> findAllRecuDemande(int page, int size);

    public EnvoyerDemandeDto findbyId(Long idEnvoyerDemande);

    public ResponseEntity<?> saveEnvoyerDemande(Long idDemande, Etat etat);

    public ResponseEntity<?> updateEnvoyerDemande(Long idDemande, Etat etat);

    public void deleteEnvoyerDemande(Long idDemande);

    public ResponseEntity<?> saveEnvoyerDemande(Long idUtilisateur);

    public List<EnvoyerDemande> getAcceptedDemande();

    public ResponseEntity<Map<String, Object>> findAllFreinds(int page, int size);
    
}
