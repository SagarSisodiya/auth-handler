package com.invento.authhandler.utils;

public class Constants {

	public static final String GROUP_CLAIMS = "groups";
	public static final String ROLE_PREFIX = "ROLE_";
	
	public static final String ROLE_INVENTO_ADMIN = "INVENTO_ADMIN";
	public static final String ROLE_INVENTO_WRITE  = "INVENTO_WRITE";
	public static final String ROLE_INVENTO_READ  = "INVENTO_READ";
	
	// JWT
	public static final String JWT_KEY = "JDIwZGVkaWNhdGVkU2VjcmV0LjAyY3VzdG9tZXIlMjRsb2dpbkBrZXk=";
	public static final String INVENTO = "Invento";
	public static final String JWT_TOKEN =  "JWT TOKEN";
	public static final String USERNAME = "username";
	public static final String AUTHORITIES = "authorities";
	public static final String JWT_HEADER = "Authorization";
	public static final String BEARER = "Bearer";
	
	//swagger
	public static final String SWAGGER_UI = "swagger-ui";
	public static final String API_DOCS = "api-docs";
	public static final String[] SWAGGER_WHITELIST = {
			"/swagger-ui/**",
			"/swagger-ui.html",
			"/v3/api-docs/**",
			"/swagger-resources/**",
			"/swagger-resources",
			"/favicon.ico"
	};
}
