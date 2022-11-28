package com.keydoorhotel.exceptions;

public class IllegalOperationException extends RuntimeException {
	private String message;

	public IllegalOperationException() {
		super();
	}

	public IllegalOperationException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
