/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.service;

import com.doranco.projectsocialmedia.dto.EnvoyerMessageDto;
import com.doranco.projectsocialmedia.entity.EnvoyerMessage;
import java.util.Map;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Assia
 */
public interface EnvoyerMessageService {

    public EnvoyerMessageDto findbyId(Long idMessage);

    public ResponseEntity<?> saveMessage(Long idUtilisateur, EnvoyerMessage message);

    public ResponseEntity<?> updateMessage(Long idMessage, EnvoyerMessage message);

    public void deleteMessage(Long idMessage);

    public ResponseEntity<Map<String, Object>> findAll(int page, int size);

    public ResponseEntity<Map<String, Object>> findAllByUtilisateur(Long idUtilisateur, int page, int size);

}
