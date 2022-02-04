/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.service.impl;

import com.doranco.projectsocialmedia.dto.ReagirDto;
import com.doranco.projectsocialmedia.entity.Publication;
import com.doranco.projectsocialmedia.entity.Reagir;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import com.doranco.projectsocialmedia.repository.PublicationRepository;
import com.doranco.projectsocialmedia.repository.ReagirRepository;
import com.doranco.projectsocialmedia.service.CurrentUserService;
import com.doranco.projectsocialmedia.service.ReagirService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
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
public class ReagirServiceImpl implements ReagirService {

    private final ReagirRepository reagirRepository;
    private final PublicationRepository publicationRepository;
    private final CurrentUserService currentUserService;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> saveReagir(Long idPublication, boolean b) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        Publication publication = publicationRepository.findById(idPublication).orElse(null);
        if (publication != null) {
            Reagir reaction = new Reagir();
            reaction.setLikeUser(currentUser);
            reaction.setLikePublication(publication);
            reaction.setLiker(b);

            return ResponseEntity
                    .status(HttpStatus.CREATED).body(this.convertEntityToDto(reagirRepository.save(reaction)));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).body("publication non trouvé");
        }
    }

    @Override
    public ReagirDto findbyId(Long idReagir) {
        return this.convertEntityToDto(reagirRepository.findById(idReagir).orElse(null));
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllByCurrentUtilisateur(int page, int size) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        List<ReagirDto> reactions;
        Pageable paging = PageRequest.of(page, size);
        Page<Reagir> pageReagirs = reagirRepository.findAllByLikeUser(currentUser, paging);
        reactions = pageReagirs.getContent()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        if (reactions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("reaction", reactions);
        response.put("currentPage", pageReagirs.getNumber());
        response.put("totalItems", pageReagirs.getTotalElements());
        response.put("totalPages", pageReagirs.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateReagir(Long idPublication, boolean b) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        Reagir reaction = reagirRepository.findByLikeUserAndLikePublication_id(currentUser, idPublication);
        if (reaction != null) {
            reaction.setLiker(b);

            return ResponseEntity
                    .status(HttpStatus.OK).body(this.convertEntityToDto(reagirRepository.save(reaction)));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).body("publication non trouvé");
        }
    }

    @Override
    public void deleteReagir(Long idPublication) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        Reagir reaction = reagirRepository.findByLikeUserAndLikePublication_id(currentUser, idPublication);
        reagirRepository.delete(reaction);
    }

    private ReagirDto convertEntityToDto(Reagir reagir) {
        return modelMapper.map(reagir, ReagirDto.class
        );

    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllByLikePublication(Long idPublication, int page, int size) {
        List<ReagirDto> reactions;
        Pageable paging = PageRequest.of(page, size);
        Page<Reagir> pageReagirs = reagirRepository.findAllByLikePublication_id(idPublication, paging);
        reactions = pageReagirs.getContent()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        if (reactions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("reaction", reactions);
        response.put("currentPage", pageReagirs.getNumber());
        response.put("totalItems", pageReagirs.getTotalElements());
        response.put("totalPages", pageReagirs.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);    }

}
