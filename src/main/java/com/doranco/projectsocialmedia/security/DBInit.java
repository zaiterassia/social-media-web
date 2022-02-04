/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.security;

import com.doranco.projectsocialmedia.entity.RoleUtilisateur;
import com.doranco.projectsocialmedia.entity.Sexe;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import com.doranco.projectsocialmedia.repository.UtilisateurRepository;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DBInit implements CommandLineRunner{
	private final UtilisateurRepository utilisateurRepository;
	private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
//        utilisateurRepository.save(new Utilisateur("zaiter", "assia", "assia-z@live.fr", passwordEncoder.encode("azerty123"),RoleUtilisateur.ADMIN, Sexe.FEMENIN, new Date(), true));
//        utilisateurRepository.save(new Utilisateur("zaiter", "adem", "adem-z@live.fr", passwordEncoder.encode("azerty124"),RoleUtilisateur.USER, Sexe.MASCULIN, new Date(), true));
//            
    }

}
