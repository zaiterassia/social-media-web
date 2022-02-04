/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.dto;

import java.util.Date;
import lombok.Data;

/**
 *
 * @author Assia
 */
@Data
public class AdhererDto {

    private Long id;
    private UtilisateurDto adhere;
    private GroupeDto groupe;
    private Date dateAdhesion;

}
