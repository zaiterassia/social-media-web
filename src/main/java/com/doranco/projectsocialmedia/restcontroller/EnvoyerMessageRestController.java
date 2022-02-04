/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.restcontroller;

import com.doranco.projectsocialmedia.dto.EnvoyerMessageDto;
import com.doranco.projectsocialmedia.entity.EnvoyerMessage;
import com.doranco.projectsocialmedia.service.EnvoyerMessageService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Assia
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/messages")
public class EnvoyerMessageRestController {

    private final EnvoyerMessageService envoyerMessageService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getListEnvoyeMessage(
            @RequestParam(required = false) Long idUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        if (idUser != null) {
            return envoyerMessageService.findAllByUtilisateur(idUser, page, size);
        } else {
            return envoyerMessageService.findAll(page, size);
        }
    }

    @GetMapping("/{id}")
    public EnvoyerMessageDto getMessageById(@PathVariable(value = "id") Long idMessage) {
        return envoyerMessageService.findbyId(idMessage);

    }

    @PostMapping("{idUtlisateur}")
    public ResponseEntity<?> envoyerMessage(
            @PathVariable(value = "idUtlisateur") Long idUtilisateur,
            @RequestBody EnvoyerMessage message
    ) {
        return envoyerMessageService.saveMessage(idUtilisateur, message);
    }

    @PutMapping("{idMessage}")
    public ResponseEntity<?> updateMessage(
            @RequestBody EnvoyerMessage message,
            @PathVariable(value = "idMessage") Long idMessage) {
        return envoyerMessageService.updateMessage(idMessage, message);
    }

    @DeleteMapping("/{idMessage}")
    public void deleteMessage(@PathVariable(value = "idMessage") Long idMessage) {
        envoyerMessageService.deleteMessage(idMessage);
    }

}
