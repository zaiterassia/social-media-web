/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

/**
 *
 * @author Assia
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class EnvoyerDemande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDemande;
    @Enumerated(EnumType.STRING)
    private Etat etat;
    @CreationTimestamp
    private Date dateEnvoi;
    @ManyToOne(optional = false)
    private Utilisateur demandeur;
    @ManyToOne(optional = false)
    private Utilisateur destinataire;
    

}
