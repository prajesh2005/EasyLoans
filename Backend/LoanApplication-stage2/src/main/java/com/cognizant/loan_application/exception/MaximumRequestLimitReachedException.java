package com.cognizant.loan_application.exception;

public class MaximumRequestLimitReachedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public MaximumRequestLimitReachedException(String message) {
		super(message);
	}

}
