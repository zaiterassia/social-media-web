package com.doranco.projectsocialmedia.security;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		LoginModel loginModel = null;
		try {
			loginModel = new ObjectMapper().readValue(request.getInputStream(), LoginModel.class);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
				new UsernamePasswordAuthenticationToken(loginModel.getEmail(), loginModel.getPassword());
		return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		DetailsUtilisateur detailsUtilisateur = (DetailsUtilisateur)authResult.getPrincipal();
		Collection<? extends GrantedAuthority> authorities = detailsUtilisateur.getAuthorities();
		String role = authorities.toArray()[0].toString();
                List<String> info = new ArrayList<>();
                info.add(Long.toString(detailsUtilisateur.getId()));
                info.add(detailsUtilisateur.getNom());
                info.add(detailsUtilisateur.getPrenom());
                info.add(detailsUtilisateur.getDescription());
                info.add(detailsUtilisateur.getAvatar());
		Date dateExpiration = new Date(System.currentTimeMillis() + 60 * 60 *1000);
		String token = JWT.create()
                                .withSubject(Long.toString(detailsUtilisateur.getId()))                                
				.withSubject(detailsUtilisateur.getUsername())
				.withExpiresAt(dateExpiration)
				.withIssuedAt(new Date())
				.withClaim("role", role)
                                .withClaim("user", info)
				.sign(Algorithm.HMAC256(SecurityProperties.SECRET));
		response.addHeader("Authorization", token);
	}
	
}
