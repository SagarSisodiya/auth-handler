package com.invento.authhandler.exception;

@SuppressWarnings("serial")
public class InvalidRequestException extends RuntimeException {
	
	private String message;
	
	public InvalidRequestException(String msg) {
		
		super(msg);
		this.message = msg;
	}

}
