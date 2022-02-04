package com.doranco.projectsocialmedia.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.doranco.projectsocialmedia.entity.Utilisateur;
import com.doranco.projectsocialmedia.repository.UtilisateurRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetailsUtilisateurService implements UserDetailsService{
	private final UtilisateurRepository utilisateurRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) {
		// TODO Auto-generated method stub
		Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
		if (utilisateur != null) {
			return new DetailsUtilisateur(utilisateur);
			
		}
		
		throw new UsernameNotFoundException(email + " pas dans la BD");
	}

}
