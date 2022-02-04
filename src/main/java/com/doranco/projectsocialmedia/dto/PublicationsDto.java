/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.dto;

import com.doranco.projectsocialmedia.entity.Categorie;
import com.doranco.projectsocialmedia.entity.Visibilite;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author Assia
 */
@Data
public class PublicationsDto {

    private Long id;
    private String texte;
    private Visibilite visibilite;
    private Date dateCreation;
    private UtilisateurDto publisher;
    private String referenceMedia;
    private String lien;
    private Date datePassage;
    private Categorie categorieEpreuve;
    private String classe;
    private int like;
    private int dislike;
    private int commentaire;

    public void setDislike(int number) {
        this.dislike = number;
    }

    public void setLike(int number) {
        this.like = number;
    }

    public void setCommenatire(int number) {
        this.commentaire = number;
    }

}
