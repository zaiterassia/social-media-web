/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.service.impl;

import com.doranco.projectsocialmedia.dto.EnvoyerMessageDto;
import com.doranco.projectsocialmedia.entity.EnvoyerMessage;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import com.doranco.projectsocialmedia.repository.EnvoyerMessageRepository;
import com.doranco.projectsocialmedia.repository.UtilisateurRepository;
import com.doranco.projectsocialmedia.service.CurrentUserService;
import com.doranco.projectsocialmedia.service.EnvoyerMessageService;
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

public class EnvoyerMessageServiceImpl implements EnvoyerMessageService {

    private final EnvoyerMessageRepository envoyerMessageRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final CurrentUserService currentUserService;
    private final ModelMapper modelMapper;

    @Override
    public EnvoyerMessageDto findbyId(Long idMessage) {
        EnvoyerMessage message =  envoyerMessageRepository.findById(idMessage).orElse(null);
        if (message != null) {
            message.setLu(true);
            envoyerMessageRepository.save(message);
        }
        return this.convertEntityToDto(envoyerMessageRepository.findById(idMessage).orElse(null));
    }

    @Override
    public ResponseEntity<?> saveMessage(Long idUtilisateur, EnvoyerMessage message) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        Utilisateur destinataire = utilisateurRepository.findById(idUtilisateur).orElse(null);
        if (destinataire != null) {
            message.setEmmetteur(currentUser);
            message.setRecepteur(destinataire);
            message.setLu(false);
            return ResponseEntity
                    .status(HttpStatus.CREATED).
                    body(this.convertEntityToDto(envoyerMessageRepository.save(message)));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).body("utilisateur non trouvé");

        }
    }

    @Override
    public ResponseEntity<?> updateMessage(Long idMessage, EnvoyerMessage message) {
        EnvoyerMessage foundMessage = envoyerMessageRepository.findById(idMessage).orElse(null);
        if (message != null) {
            message.setContenu(message.getContenu());
            return ResponseEntity
                    .status(HttpStatus.CREATED).body(this.convertEntityToDto(envoyerMessageRepository.save(foundMessage)));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).body("message non trouvé");
        }
    }

    @Override
    public void deleteMessage(Long idMessage) {
        envoyerMessageRepository.deleteById(idMessage);
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAll(int page, int size) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        List<EnvoyerMessageDto> messages;
        Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "dateEnvoie"));
        Page<EnvoyerMessage> pageEnvoyerMessages = envoyerMessageRepository.findAllByEmmetteurOrRecepteur(currentUser, currentUser, paging);
        messages = pageEnvoyerMessages.getContent()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        if (messages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", messages);
        response.put("currentPage", pageEnvoyerMessages.getNumber());
        response.put("totalItems", pageEnvoyerMessages.getTotalElements());
        response.put("totalPages", pageEnvoyerMessages.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>>  findAllByUtilisateur(Long idUtilisateur, int page, int size) {
       Utilisateur currentUser = currentUserService.getCurrentUser();
        Utilisateur destinataire = utilisateurRepository.findById(idUtilisateur).orElse(null);
        if (destinataire != null) {
            List<EnvoyerMessageDto> messages;
            Pageable paging = PageRequest.of(page, size);
            Page<EnvoyerMessage> pageEnvoyerMessages = envoyerMessageRepository.findAllByUtilisateur(currentUser, destinataire, paging);
            messages = pageEnvoyerMessages.getContent()
                    .stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());

            if (messages.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("message", messages);
            response.put("currentPage", pageEnvoyerMessages.getNumber());
            response.put("totalItems", pageEnvoyerMessages.getTotalElements());
            response.put("totalPages", pageEnvoyerMessages.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    private EnvoyerMessageDto convertEntityToDto(EnvoyerMessage message) {
        return modelMapper.map(message, EnvoyerMessageDto.class
        );

    }

}
