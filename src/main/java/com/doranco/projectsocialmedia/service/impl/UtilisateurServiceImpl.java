/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.service.impl;

import com.doranco.projectsocialmedia.dto.UtilisateurDto;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import com.doranco.projectsocialmedia.repository.UtilisateurRepository;
import com.doranco.projectsocialmedia.service.UtilisateurService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Assia
 */
@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public UtilisateurDto findbyId(Long id) {
        return this.convertEntityToDto(utilisateurRepository.findById(id).orElse(null));
    }

    @Override
    public ResponseEntity<Map<String, Object>> lister(int page, int size) {
        List<UtilisateurDto> utilisateurs;
        Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "nom"));
        Page<Utilisateur> pageUtilisateurs = utilisateurRepository.findAll(paging);
        utilisateurs = pageUtilisateurs.getContent()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        if (utilisateurs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("utilisateurs", utilisateurs);
        response.put("currentPage", pageUtilisateurs.getNumber());
        response.put("totalItems", pageUtilisateurs.getTotalElements());
        response.put("totalPages", pageUtilisateurs.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    @Override
    public ResponseEntity<?> saveUtilisateur(Utilisateur utilisateur) {
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        return ResponseEntity
                .status(HttpStatus.CREATED).body(this.convertEntityToDto(utilisateurRepository.save(utilisateur)));
    }

    @Override
    public void updateUtilisateur(Long id, Utilisateur utilisateur) {
        utilisateurRepository.findById(id).ifPresent(utilisateurAmodifier -> {
            utilisateurAmodifier.setNom(utilisateur.getNom());
            utilisateurAmodifier.setPrenom(utilisateur.getPrenom());
            utilisateurAmodifier.setEmail(utilisateur.getEmail());
            utilisateurAmodifier.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
            utilisateurAmodifier.setRole(utilisateur.getRole());
            utilisateurAmodifier.setSexe(utilisateur.getSexe());
            utilisateurAmodifier.setTelephone(utilisateur.getTelephone());
            utilisateurAmodifier.setAvatar(utilisateur.getAvatar());
            utilisateurAmodifier.setDescription(utilisateur.getDescription());

            utilisateurRepository.save(utilisateurAmodifier);
        });
    }

    @Override

    public void deleteById(Long id) {
        utilisateurRepository.deleteById(id);
    }

    @Override
    public void activateUtilisateur(Long id) {
        Utilisateur utilisateurActiver = utilisateurRepository.findById(id).orElse(null);
        if (utilisateurActiver != null) {
            utilisateurActiver.setActif(true);
            utilisateurRepository.save(utilisateurActiver);

        }
    }

    @Override
    public void desactivateUtilisateur(Long id) {
        Utilisateur utilisateurActiver = utilisateurRepository.findById(id).orElse(null);
        if (utilisateurActiver != null) {
            utilisateurActiver.setActif(false);
            utilisateurRepository.save(utilisateurActiver);

        }
    }

    @Override
    public UtilisateurDto convertEntityToDto(Utilisateur utilisateur) {
        return modelMapper.map(utilisateur, UtilisateurDto.class);
    }

}
