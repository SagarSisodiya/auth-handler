package com.invento.authhandler.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.invento.authhandler.exception.AuthorityNotFoundException;
import com.invento.authhandler.exception.ErrorResponse;
import com.invento.authhandler.exception.InvalidRequestException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = InvalidRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorResponse
		handleException(InvalidRequestException ex) {
		
		log.error(ex.getMessage());
		return new ErrorResponse(ex.getMessage());
	}
	
	@ExceptionHandler(value = AuthorityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody ErrorResponse
		handleException(AuthorityNotFoundException ex) {
		
		log.error(ex.getMessage());
		return new ErrorResponse(ex.getMessage());
	}
	
	@ExceptionHandler(value = AuthenticationException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public @ResponseBody ErrorResponse
		handleException(AuthenticationException ex) {
		
		log.error("AuthenticationException. Error: " + ex.getMessage());
		return new ErrorResponse(ex.getMessage());
	}
	
	@ExceptionHandler(value = InternalServerError.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorResponse
		handleException(InternalServerError ex) {
		
		log.error(ex.getMessage());
		return new ErrorResponse(ex.getMessage());
	}
}
