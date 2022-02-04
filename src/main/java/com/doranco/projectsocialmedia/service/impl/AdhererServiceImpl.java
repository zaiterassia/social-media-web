/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.service.impl;

import com.doranco.projectsocialmedia.dto.AdhererDto;
import com.doranco.projectsocialmedia.dto.GroupeDto;
import com.doranco.projectsocialmedia.dto.UtilisateurDto;
import com.doranco.projectsocialmedia.entity.Adherer;
import com.doranco.projectsocialmedia.entity.Groupe;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import com.doranco.projectsocialmedia.repository.AdhererRepository;
import com.doranco.projectsocialmedia.repository.GroupeRepository;
import com.doranco.projectsocialmedia.service.AdhererService;
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
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author Assia
 */
@Service
@RequiredArgsConstructor
public class AdhererServiceImpl implements AdhererService {

    private final AdhererRepository adhererRepository;
    private final GroupeRepository groupeRepository;
    private final CurrentUserService currentUserService;
    private final ModelMapper modelMapper;

    @Override
    public AdhererDto findbyId(Long idAdherer) {
        return this.convertEntityToDto(adhererRepository.findById(idAdherer).orElse(null));
    }

    @Override
    public ResponseEntity<?> saveAdhesion(Long idGroupe) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        Groupe groupe = groupeRepository.findById(idGroupe).orElse(null);
        if (groupe != null) {
            Adherer adhesion = new Adherer();
            adhesion.setAdhere(currentUser);
            adhesion.setGroupe(groupe);

            return ResponseEntity
                    .status(HttpStatus.CREATED).body(this.convertEntityToDto(adhererRepository.save(adhesion)));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).body("groupe non trouvé");
        }
    }

    @Override
    public void deleteAdhesion(Long idGroupe) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        Groupe groupe = groupeRepository.findById(idGroupe).orElse(null);
        Adherer adhesion = adhererRepository.findByAdhereAndGroupe(currentUser, groupe);
        adhererRepository.delete(adhesion);

    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllByCurrentUtilisateur(int page, int size) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        List<AdhererDto> adherers;
        Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateAdhesion"));
        Page<Adherer> pageAdherers = adhererRepository.findAllByAdhere(currentUser, paging);
        adherers = pageAdherers.getContent()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        if (adherers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("adherers", adherers);
        response.put("currentPage", pageAdherers.getNumber());
        response.put("totalItems", pageAdherers.getTotalElements());
        response.put("totalPages", pageAdherers.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> listerGroupe(int page, int size) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        List<GroupeDto> groupes;
        Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateAdhesion"));
        Page<Adherer> pageAdherers = adhererRepository.findAllByAdhere(currentUser, paging);
        groupes = pageAdherers.getContent()
                .stream()
                .map(Adherer::getGroupe)
                .collect(Collectors.toList())
                .stream()
                .map(this::convertGroupeToDto)
                .collect(Collectors.toList());

        if (groupes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("groupes", groupes);
        response.put("currentPage", pageAdherers.getNumber());
        response.put("totalItems", pageAdherers.getTotalElements());
        response.put("totalPages", pageAdherers.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> listerMembres(Long idGroupe, int page, int size) {
        List<UtilisateurDto> members;
        Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateAdhesion"));
        Page<Adherer> pageAdherers = adhererRepository.findAllByGroupe_id(idGroupe, paging);
        members = pageAdherers.getContent()
                .stream()
                .map(Adherer::getAdhere)
                .collect(Collectors.toList())
                .stream()
                .map(this::convertUserToDto)
                .collect(Collectors.toList());

        if (members.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("members", members);
        response.put("currentPage", pageAdherers.getNumber());
        response.put("totalItems", pageAdherers.getTotalElements());
        response.put("totalPages", pageAdherers.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private AdhererDto convertEntityToDto(Adherer adherer) {
        return modelMapper.map(adherer, AdhererDto.class
        );

    }

    private GroupeDto convertGroupeToDto(Groupe groupe) {
        return modelMapper.map(groupe, GroupeDto.class);
    }

    private UtilisateurDto convertUserToDto(Utilisateur utilisateur) {
        return modelMapper.map(utilisateur, UtilisateurDto.class);
    }

}
