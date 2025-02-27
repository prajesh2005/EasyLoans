package com.cognizant.loan_application.exception;

public class DuplicateApplicationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DuplicateApplicationException(String message) {
		super(message);
	}
}
