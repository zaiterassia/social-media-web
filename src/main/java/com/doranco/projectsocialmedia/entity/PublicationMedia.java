/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.entity;


import javax.persistence.Entity;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Assia
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PublicationMedia extends Publication {
    private String referenceMedia;
    
}
