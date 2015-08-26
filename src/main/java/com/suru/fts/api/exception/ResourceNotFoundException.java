package com.suru.fts.api.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;


	public ResourceNotFoundException() {
		super("Resource not found");
	}


	public ResourceNotFoundException(final String message) {
		super(message);
	}
}
