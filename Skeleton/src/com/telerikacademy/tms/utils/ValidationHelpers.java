package com.telerikacademy.tms.utils;

public class ValidationHelpers {

	public static void validateInRange(int value, int min, int max, String message) {
		if (value < min || value > max) {
			throw new IllegalArgumentException(message);
		}
	}

}
