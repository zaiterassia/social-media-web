/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.service.impl;

import com.doranco.projectsocialmedia.dto.GroupeDto;
import com.doranco.projectsocialmedia.entity.Groupe;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import com.doranco.projectsocialmedia.repository.GroupeRepository;
import com.doranco.projectsocialmedia.service.CurrentUserService;
import com.doranco.projectsocialmedia.service.GroupeService;
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
public class GroupeServiceImpl implements GroupeService {

    private final GroupeRepository groupeRepository;
    private final CurrentUserService currentUserService;
    private final ModelMapper modelMapper;

    @Override
    public GroupeDto findbyId(Long id) {
        return this.convertEntityToDto(groupeRepository.findById(id).orElse(null));
    }

    @Override
    public ResponseEntity<Map<String, Object>> lister(int page, int size) {
        List<GroupeDto> groupes;
        Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateCreation"));
        Page<Groupe> pageGroupes = groupeRepository.findAll(paging);
        groupes = pageGroupes.getContent()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        if (groupes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("groupes", groupes);
        response.put("currentPage", pageGroupes.getNumber());
        response.put("totalItems", pageGroupes.getTotalElements());
        response.put("totalPages", pageGroupes.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> saveGroupe(Groupe groupe) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        groupe.setAdministrateur(currentUser);
        return ResponseEntity
                .status(HttpStatus.CREATED).body(this.convertEntityToDto(groupeRepository.save(groupe)));
    }

    @Override
    public void updateGroupe(Long id, Groupe groupe) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        groupeRepository.findById(id).ifPresent(groupeAmodifier -> {
            if (currentUser.equals(groupeAmodifier.getAdministrateur())) {
                groupeAmodifier.setLibelle(groupe.getLibelle());
                groupeAmodifier.setDescription(groupe.getDescription());

                groupeRepository.save(groupeAmodifier);
            }
        });
    }

    @Override
    public void deleteById(Long id) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        groupeRepository.findById(id).ifPresent(groupeAsupprimer -> {
            if (currentUser.equals(groupeAsupprimer.getAdministrateur())) {
                groupeRepository.deleteById(id);
            }
        });
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllByCurrentUtilisateur(int page, int size) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
                List<GroupeDto> groupes;
        Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateCreation"));
        Page<Groupe> pageGroupes = groupeRepository.findAllByAdministrateur(currentUser,paging);
        groupes = pageGroupes.getContent()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        if (groupes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("groupes", groupes);
        response.put("currentPage", pageGroupes.getNumber());
        response.put("totalItems", pageGroupes.getTotalElements());
        response.put("totalPages", pageGroupes.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
        @Override
    public void activateGroupe(Long id) {
        Groupe groupeActiver = groupeRepository.findById(id).orElse(null);
        if (groupeActiver != null) {
            groupeActiver.setActif(true);
            groupeRepository.save(groupeActiver);
        }
    }

    @Override
    public void desactivateGroupe(Long id) {
        Groupe groupeActiver = groupeRepository.findById(id).orElse(null);
        if (groupeActiver != null) {
            groupeActiver.setActif(false);
            groupeRepository.save(groupeActiver);
        }
    }
@Override
    public GroupeDto convertEntityToDto(Groupe groupe) {
                GroupeDto groupeDto = new GroupeDto();
        groupeDto
                = modelMapper.map(groupe, GroupeDto.class
                );
        return groupeDto;
    }

}
