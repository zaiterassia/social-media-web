/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.dto;


import com.doranco.projectsocialmedia.entity.Sexe;
import lombok.Data;

/**
 *
 * @author Assia
 */
@Data
public class UtilisateurDto {
    private Long id;   
    private String nom;
    private String prenom;
    private String email;
    private Sexe sexe;
    private String telephone;
    private String avatar;
    private String description;
}
