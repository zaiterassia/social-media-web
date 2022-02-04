/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.doranco.projectsocialmedia.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   
    private String nom;
    private String prenom;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleUtilisateur role;
    @Enumerated(EnumType.STRING)
    private Sexe sexe;
    private boolean actif;
    private String telephone;
    private String avatar;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(updatable = false)
    @CreationTimestamp
    private Date dateCreation;
    @UpdateTimestamp
    private Date dateModification;
    @OneToMany(mappedBy = "administrateur", orphanRemoval = true)
    private List<Groupe> groupes = new ArrayList<>();
    @OneToMany(mappedBy = "adhere", orphanRemoval = true)
    private List<Adherer> adhesions = new ArrayList<>();
    @OneToMany(mappedBy = "emmetteur", orphanRemoval = true)
    private List<EnvoyerMessage> messagesEnvoye = new ArrayList<>();
    @OneToMany(mappedBy = "recepteur", orphanRemoval = true)
    private List<EnvoyerMessage> messagesRecu = new ArrayList<>();
    @OneToMany(mappedBy = "demandeur", orphanRemoval = true)
    private List<EnvoyerDemande> demandesEnvoye = new ArrayList<>();
    @OneToMany(mappedBy = "destinataire", orphanRemoval = true)
    private List<EnvoyerDemande> demandesRecu = new ArrayList<>();
    @OneToMany(mappedBy = "publisher", orphanRemoval = true)
    @JsonIgnore
    private List<Publication> publications = new ArrayList<>();
    @OneToMany(mappedBy = "likeUser", orphanRemoval = true)
    private List<Reagir> likepublications = new ArrayList<>();
    @OneToMany(mappedBy = "commentaireUser", orphanRemoval = true)
    private List<Commentaire> commentaires = new ArrayList<>();
    

    public Utilisateur(String nom, String prenom, String email, String password, RoleUtilisateur role, Sexe sexe, Date dateCreation, boolean actif) {
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.email = email;
        this.role = role;
        this.dateCreation = dateCreation;
        this.actif = actif;
    }

    public Utilisateur(String nom, String prenom, String email, String password, RoleUtilisateur role, Sexe sexe, boolean actif, String telephone, String avatar, String description) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
        this.sexe = sexe;
        this.actif = actif;
        this.telephone = telephone;
        this.avatar = avatar;
        this.description = description;
    }



}
