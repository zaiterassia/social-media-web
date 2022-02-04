package com.doranco.projectsocialmedia.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.doranco.projectsocialmedia.entity.Utilisateur;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DetailsUtilisateur implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Utilisateur utilisateur;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().toString());
		
		return Collections.singletonList(grantedAuthority);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return utilisateur.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return utilisateur.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return utilisateur.isActif();
	}
        
        public Long getId() {
            return utilisateur.getId();
        }
        
        public String getNom() {
            return utilisateur.getNom();
        }
        
        public String getPrenom() {
            return utilisateur.getPrenom();
        }
                
        public String getDescription() {
            return utilisateur.getDescription();
        }
        public String getAvatar() {
            return utilisateur.getAvatar();
        }

                                


}
