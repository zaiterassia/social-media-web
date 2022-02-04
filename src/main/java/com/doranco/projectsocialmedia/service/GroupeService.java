/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.service;

import com.doranco.projectsocialmedia.dto.GroupeDto;
import com.doranco.projectsocialmedia.entity.Groupe;
import java.util.Map;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Assia
 */
public interface GroupeService {

    GroupeDto findbyId(Long id);

    ResponseEntity<Map<String, Object>> lister(int page, int size);

    ResponseEntity<?> saveGroupe(Groupe groupe);

    void updateGroupe(Long id, Groupe groupe);

    void deleteById(Long id);

    ResponseEntity<Map<String, Object>> findAllByCurrentUtilisateur(int page, int size);

    void activateGroupe(Long id);

    void desactivateGroupe(Long id);

    public GroupeDto convertEntityToDto(Groupe groupe);

}
