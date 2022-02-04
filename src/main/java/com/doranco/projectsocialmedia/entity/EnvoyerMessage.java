/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class EnvoyerMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMessage;
    @Column(columnDefinition = "TEXT")
    private String contenu;
    @Column(updatable = false)
    @CreationTimestamp
    private Date dateEnvoie;
    private boolean lu;
    @ManyToOne(optional = false)
    private Utilisateur emmetteur;
    @ManyToOne(optional = false)
    private Utilisateur recepteur;

}
