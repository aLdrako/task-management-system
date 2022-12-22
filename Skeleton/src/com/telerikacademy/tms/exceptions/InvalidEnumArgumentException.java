package com.telerikacademy.tms.exceptions;

public class InvalidEnumArgumentException extends RuntimeException {
	public InvalidEnumArgumentException() {
	}

	public InvalidEnumArgumentException(String message) {
		super(message);
	}
}
