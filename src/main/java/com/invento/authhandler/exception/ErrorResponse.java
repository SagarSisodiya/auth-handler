package com.invento.authhandler.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

	private String message;
	
	public ErrorResponse(String msg) {
		
		super();
		this.message = msg;
	}
}
