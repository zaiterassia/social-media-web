/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.doranco.projectsocialmedia.service;

import com.doranco.projectsocialmedia.dto.AdhererDto;
import java.util.Map;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Assia
 */
public interface AdhererService {

    ResponseEntity<Map<String, Object>> findAllByCurrentUtilisateur(int page, int size);

    ResponseEntity<Map<String, Object>> listerGroupe(int page, int size);

    ResponseEntity<Map<String, Object>> listerMembres(Long idGroupe, int page, int size);

    AdhererDto findbyId(Long idAdherer);

    ResponseEntity<?> saveAdhesion(Long idGroupe);

    void deleteAdhesion(Long idGroupe);

}
