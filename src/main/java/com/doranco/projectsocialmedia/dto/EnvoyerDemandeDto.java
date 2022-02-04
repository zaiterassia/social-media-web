/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.dto;

import com.doranco.projectsocialmedia.entity.Etat;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author Assia
 */

@Data
public class EnvoyerDemandeDto {
    private Long idDemande;
    private Etat etat;
    private Date dateEnvoi;
    private UtilisateurDto demandeur;
    private UtilisateurDto destinataire;
    
}
