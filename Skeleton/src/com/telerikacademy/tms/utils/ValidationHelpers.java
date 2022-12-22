package com.telerikacademy.tms.utils;

import java.util.List;

public class ValidationHelpers {

	private static final String INVALID_NUMBER_OF_ARGUMENTS = "Invalid number of arguments. Expected: %d; received: %d.";

	public static void validateInRange(int value, int min, int max, String message) {
		if (value < min || value > max) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void validateArgumentsCount(List<String> list, int expectedNumberOfParameters) {
		if (list.size() != expectedNumberOfParameters) {
			throw new IllegalArgumentException(
					String.format(INVALID_NUMBER_OF_ARGUMENTS, expectedNumberOfParameters, list.size())
			);
		}
	}

	public static void validateArgumentsCountTill(List<String> list, int expectedNumberOfParameters) {
		if (list.size() > expectedNumberOfParameters) {
			throw new IllegalArgumentException(
					String.format(INVALID_NUMBER_OF_ARGUMENTS, expectedNumberOfParameters, list.size())
			);
		}
	}

}
