package com.invento.authhandler.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.invento.authhandler.utils.Constants;
import com.nimbusds.jose.util.StandardCharset;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTTokenGeneratorFilter extends OncePerRequestFilter{
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null) {
			SecretKey key = Keys.hmacShaKeyFor(Constants.JWT_KEY.getBytes(StandardCharset.UTF_8));
			
			String jwt = Jwts.builder().setIssuer(Constants.INVENTO).setSubject(Constants.JWT_TOKEN)
					.claim(Constants.USERNAME, authentication.getName())
					.claim(Constants.AUTHORITIES, populateAuthorities(authentication.getAuthorities()))
					.setIssuedAt(new Date())
					.setExpiration(new Date((new Date()).getTime() + 30000000))
					.signWith(key).compact();
			response.setHeader(Constants.JWT_HEADER, jwt);
		}
		filterChain.doFilter(request, response);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return !request.getServletPath().equals("/auth/login");
	}
	
	private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Set<String> authoritySet = new HashSet<>();
		for(GrantedAuthority authority: authorities) {
			authoritySet.add(authority.getAuthority());
		}
		return String.join(",", authoritySet);
	}
}
