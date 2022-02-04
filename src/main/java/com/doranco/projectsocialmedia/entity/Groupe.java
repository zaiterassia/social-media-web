/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author Assia
 */
@Entity
@Getter
@Setter

public class Groupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    @Column(columnDefinition = "TEXT")
    private String Description;
    @ManyToOne(optional = false)
    private Utilisateur administrateur;
    @Column(updatable = false)
    @CreationTimestamp
    private Date dateCreation;
    @UpdateTimestamp
    private Date dateModification;
    @OneToMany(mappedBy = "groupe") 
    private List<Adherer> utilisateurAdhesion = new ArrayList<>();
    private boolean actif;
    



}
