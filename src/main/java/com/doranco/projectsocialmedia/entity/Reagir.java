/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
public class Reagir {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean liker;
    @ManyToOne(optional = false)
    private Utilisateur likeUser;
    @ManyToOne(optional = false)
    private Publication likePublication;
    
    public boolean getLike() {
        return this.liker;
    }
    
}
