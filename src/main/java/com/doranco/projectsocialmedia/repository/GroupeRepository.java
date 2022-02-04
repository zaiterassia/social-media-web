/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.doranco.projectsocialmedia.repository;

import com.doranco.projectsocialmedia.entity.Groupe;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Assia
 */
public interface GroupeRepository extends JpaRepository<Groupe, Long>{

    public Page<Groupe> findAllByAdministrateur(Utilisateur utilisateur, Pageable paging);

}
