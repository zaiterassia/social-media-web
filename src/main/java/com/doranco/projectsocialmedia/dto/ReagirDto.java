/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.dto;

import lombok.Data;

/**
 *
 * @author Assia
 */
@Data
public class ReagirDto {
    private Long id;
    private boolean liker;
    private UtilisateurDto likeUser;
    private PublicationsDto likePublication;
    
}
