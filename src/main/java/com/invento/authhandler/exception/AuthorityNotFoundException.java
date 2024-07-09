package com.invento.authhandler.exception;

@SuppressWarnings("serial")
public class AuthorityNotFoundException extends RuntimeException {

	private String message;
	
	public AuthorityNotFoundException(String message) {
		super(message);
		this.message = message;
	}
}
