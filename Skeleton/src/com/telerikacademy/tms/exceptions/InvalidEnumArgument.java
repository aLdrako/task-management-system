package com.telerikacademy.tms.exceptions;

public class InvalidEnumArgument extends RuntimeException {
	public InvalidEnumArgument() {
	}

	public InvalidEnumArgument(String message) {
		super(message);
	}
}
