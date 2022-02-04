/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.service.impl;

import com.doranco.projectsocialmedia.dto.EnvoyerDemandeDto;
import com.doranco.projectsocialmedia.entity.EnvoyerDemande;
import com.doranco.projectsocialmedia.entity.Etat;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import com.doranco.projectsocialmedia.repository.EnvoyerDemandeRepository;
import com.doranco.projectsocialmedia.repository.UtilisateurRepository;
import com.doranco.projectsocialmedia.service.CurrentUserService;
import com.doranco.projectsocialmedia.service.EnvoyerDemandeService;
import com.doranco.projectsocialmedia.service.UtilisateurService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
public class EnvoyerDemandeServiceImpl implements EnvoyerDemandeService {

    private final EnvoyerDemandeRepository envoyerDemandeRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurService utilisateurService;
    private final CurrentUserService currentUserService;
    private final ModelMapper modelMapper;

    @Override
    public EnvoyerDemandeDto findbyId(Long idEnvoyerDemande) {
        return this.convertEntityToDto(envoyerDemandeRepository.findById(idEnvoyerDemande).orElse(null));
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllEnvoyeDemande(int page, int size) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
            List<EnvoyerDemandeDto> demandes;
            Pageable paging = PageRequest.of(page, size);
            Page<EnvoyerDemande> pageEnvoyerDemandes = envoyerDemandeRepository.findAllByDemandeur(currentUser,paging);
            demandes = pageEnvoyerDemandes.getContent()
                    .stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());

            if (demandes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("message", demandes);
            response.put("currentPage", pageEnvoyerDemandes.getNumber());
            response.put("totalItems", pageEnvoyerDemandes.getTotalElements());
            response.put("totalPages", pageEnvoyerDemandes.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllRecuDemande(int page, int size) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
            List<EnvoyerDemandeDto> demandes;
            Pageable paging = PageRequest.of(page, size);
            Page<EnvoyerDemande> pageEnvoyerDemandes = envoyerDemandeRepository.findAllByDestinataire(currentUser,paging);
            demandes = pageEnvoyerDemandes.getContent()
                    .stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());

            if (demandes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("message", demandes);
            response.put("currentPage", pageEnvoyerDemandes.getNumber());
            response.put("totalItems", pageEnvoyerDemandes.getTotalElements());
            response.put("totalPages", pageEnvoyerDemandes.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> saveEnvoyerDemande(Long idDemande, Etat etat) {
        EnvoyerDemande demande = envoyerDemandeRepository.findById(idDemande).orElse(null);
        if (demande != null) {
            demande.setEtat(etat);
            return ResponseEntity
                    .status(HttpStatus.CREATED).body(this.convertEntityToDto(envoyerDemandeRepository.save(demande)));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).body("demande non trouvé");
        }
    }

    @Override
    public ResponseEntity<?> updateEnvoyerDemande(Long idDemande, Etat etat) {
        return this.saveEnvoyerDemande(idDemande, etat);
    }

    @Override
    public ResponseEntity<?> saveEnvoyerDemande(Long idUtilisateur) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        Utilisateur destinataire = utilisateurRepository.findById(idUtilisateur).orElse(null);
        if (destinataire != null) {
            EnvoyerDemande demande = new EnvoyerDemande();
            demande.setDemandeur(currentUser);
            demande.setDestinataire(destinataire);
            demande.setEtat(Etat.ATTENTE);
            return ResponseEntity
                    .status(HttpStatus.CREATED).
                    body(this.convertEntityToDto(envoyerDemandeRepository.save(demande)));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).body("utilisateur non trouvé");

        }
    }
    
    @Override
    public List<EnvoyerDemande> getAcceptedDemande() {
        Utilisateur user = currentUserService.getCurrentUser();
        return envoyerDemandeRepository.findAllAccepted(user, Etat.ACCEPTER);
    }

    @Override
    public void deleteEnvoyerDemande(Long idDemande) {
        envoyerDemandeRepository.deleteById(idDemande);

    }

    private EnvoyerDemandeDto convertEntityToDto(EnvoyerDemande demande) {
        return modelMapper.map(demande, EnvoyerDemandeDto.class
        );

    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllFreinds(int page, int size) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        List<Utilisateur> amis = new ArrayList<Utilisateur>();
        List<EnvoyerDemande> liste = this.getAcceptedDemande();
        for (EnvoyerDemande demande : liste) {
            if (demande.getDemandeur().equals(currentUser))
                amis.add(demande.getDestinataire());
            else 
                amis.add(demande.getDemandeur());
        }
        
        if (amis.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Page<Utilisateur> pageUtilisateurs = new PageImpl<Utilisateur>(amis, PageRequest.of(page, size), amis.size());

        Map<String, Object> response = new HashMap<>();
        response.put("utilisateurs", amis.stream().map(utilisateurService::convertEntityToDto)
                    .collect(Collectors.toList()));
        response.put("currentPage", pageUtilisateurs.getNumber());
        response.put("totalItems", pageUtilisateurs.getTotalElements());
        response.put("totalPages", pageUtilisateurs.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
        
}

