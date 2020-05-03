package com.cg.LoanProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LoanExceptionHandler {
	@ExceptionHandler(AccountAlreadyExistsException.class)
	public ResponseEntity<Object> exception1(Exception e){
		return new ResponseEntity<Object>(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Object> exception2(Exception e){
		return new ResponseEntity<Object>(e.getLocalizedMessage(),HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(LoanHandlingExceptions.class)
	public ResponseEntity<Object> exception3(Exception e){
		return new ResponseEntity<Object>(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
	}
}