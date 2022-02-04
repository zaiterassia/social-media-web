/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.entity;


import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import lombok.AllArgsConstructor;

/**
 *
 * @author Assia
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


@Inheritance(strategy = InheritanceType.JOINED)
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Visibilite visibilite;
    @Column(columnDefinition = "TEXT")
    private String texte;
    @Column(updatable = false)
    @CreationTimestamp
    private Date dateCreation;
    @UpdateTimestamp
    private Date dateModification;
    @ManyToOne(optional = false)
    private Utilisateur publisher;
    @OneToMany(mappedBy = "likePublication", orphanRemoval = true)
    private List<Reagir> likePublication;
    @OneToMany(mappedBy = "commentairePublication", orphanRemoval = true)
    private List<Commentaire> commentairesPublication;
    


}
