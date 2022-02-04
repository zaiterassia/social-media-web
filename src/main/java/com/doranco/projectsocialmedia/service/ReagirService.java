/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.service;

import com.doranco.projectsocialmedia.dto.ReagirDto;
import java.util.Map;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Assia
 */
public interface ReagirService {

    ResponseEntity<?> saveReagir(Long idPublication, boolean b);

    ReagirDto findbyId(Long idReagir);

    ResponseEntity<Map<String, Object>> findAllByCurrentUtilisateur(int page, int size);
    
    ResponseEntity<Map<String, Object>> findAllByLikePublication(Long idPublication, int page, int size);

    ResponseEntity<?> updateReagir(Long idPublication, boolean b);

    void deleteReagir(Long idPublication);

}
