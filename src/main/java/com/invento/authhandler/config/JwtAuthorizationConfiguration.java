package com.invento.authhandler.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.invento.authhandler.filter.JWTTokenGeneratorFilter;
import com.invento.authhandler.filter.JWTTokenValidatorFilter;
import com.invento.authhandler.service.AuthService;
import com.invento.authhandler.support.GroupsClaimMapper;
import com.invento.authhandler.support.NamedOidcUser;
import com.invento.authhandler.utils.Constants;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class JwtAuthorizationConfiguration {
	
	private AuthService authService;
	
	JwtAuthorizationConfiguration(AuthService authService) {
		this.authService = authService;
	}

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

		http
			.csrf(csrf -> csrf.disable())
			.cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
				@Override
				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
					CorsConfiguration config = new CorsConfiguration();
					config.setAllowedMethods(Collections.singletonList("*"));					
					config.setAllowedOrigins(Collections.singletonList("*"));					
					config.setAllowedHeaders(Collections.singletonList("*"));
					config.setAllowCredentials(true);
					config.setMaxAge(3600L);
					return config;
				}
			}))
			.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
			.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
			.authorizeHttpRequests(request -> request
				.requestMatchers(Constants.SWAGGER_WHITELIST).permitAll()
				.requestMatchers("/auth/addAuthority", "/auth/deleteAuthority", "/auth/updateAuthority")
					.hasAnyRole(Constants.ROLE_INVENTO_ADMIN, Constants.ROLE_INVENTO_WRITE)
				.requestMatchers("/auth/getAuthorities")
					.hasAnyRole(Constants.ROLE_INVENTO_ADMIN, Constants.ROLE_INVENTO_WRITE, 
						Constants.ROLE_INVENTO_READ)
				.requestMatchers("/auth/**").authenticated())
			.oauth2Login(oauth2 -> oauth2.userInfoEndpoint(e -> 
				e.oidcUserService(customOidcUserService())))
			.httpBasic(Customizer.withDefaults());
		return http.build();
	}
	
	private OAuth2UserService<OidcUserRequest, OidcUser> customOidcUserService() {
		
		final OidcUserService delegate = new OidcUserService();
		final GroupsClaimMapper mapper = 
			new GroupsClaimMapper(Constants.ROLE_PREFIX, 
				Constants.GROUP_CLAIMS, authService.getAuthorityList());
		
		return userRequest -> {
			OidcUser oidcUser = delegate.loadUser(userRequest);
			Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
			mappedAuthorities.addAll(oidcUser.getAuthorities());
			mappedAuthorities.addAll(mapper.mapAuthorities(oidcUser));
			
			oidcUser = new NamedOidcUser(mappedAuthorities, oidcUser.getIdToken(), 
				oidcUser.getUserInfo(), oidcUser.getName());
			
			return oidcUser;
		};
	}
}
