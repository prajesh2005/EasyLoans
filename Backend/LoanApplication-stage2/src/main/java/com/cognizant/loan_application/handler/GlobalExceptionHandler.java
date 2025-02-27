package com.cognizant.loan_application.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cognizant.loan_application.exception.DuplicateApplicationException;
import com.cognizant.loan_application.exception.LoanApplicationCreationException;
import com.cognizant.loan_application.exception.LoanApplicationNotFoundException;
import com.cognizant.loan_application.exception.MaximumRequestLimitReachedException;
import com.cognizant.loan_application.exception.UnauthorizedException;
import com.cognizant.loan_application.exception.UserAlreadyExistsException;

@RestControllerAdvice
public class GlobalExceptionHandler {

//	
	@ExceptionHandler(MaximumRequestLimitReachedException.class)
	public ResponseEntity<String> handleMaximumRequestLimitReachedException(MaximumRequestLimitReachedException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

//	
	@ExceptionHandler(LoanApplicationNotFoundException.class)
	public ResponseEntity<String> handleLoanApplicationNotFoundException(LoanApplicationNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

//	
	@ExceptionHandler(DuplicateApplicationException.class)
	public ResponseEntity<String> handleDuplicateApplicationException(DuplicateApplicationException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	}

//	
	@ExceptionHandler(LoanApplicationCreationException.class)
	public ResponseEntity<String> handleLoanApplicationCreationException(LoanApplicationCreationException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

//	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<String> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			errors.add(error.getDefaultMessage());
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

//	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}

//
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	}

}