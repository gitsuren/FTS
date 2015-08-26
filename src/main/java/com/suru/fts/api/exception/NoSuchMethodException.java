package com.suru.fts.api.exception;

public class NoSuchMethodException extends RuntimeException {

	private static final long serialVersionUID = 1L;


	public NoSuchMethodException() {
		super("Resource not found");
	}


	public NoSuchMethodException(final String message) {
		super(message);
	}
}
