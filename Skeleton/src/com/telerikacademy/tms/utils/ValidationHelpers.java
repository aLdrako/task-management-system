package com.telerikacademy.tms.utils;

import java.util.List;

import static java.lang.String.format;

public class ValidationHelpers {

	private static final String INVALID_NUMBER_OF_ARGUMENTS = "Invalid number of arguments. Expected: %d; received: %d.";
	private static final String INVALID_NUMBER_OF_ARGUMENTS_TILL = "Invalid number of arguments. Expected: from %d to %d; received: %d.";
	private static final String INVALID_NUMBER_OF_ARGUMENTS_MIN = "Invalid number of arguments. Expected: at least %d; received: %d.";

	public static void validateInRange(int value, int min, int max, String message) {
		if (value < min || value > max) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void validateArgumentsCount(List<String> list, int expectedNumberOfParameters) {
		if (list.size() != expectedNumberOfParameters) {
			throw new IllegalArgumentException(
					format(INVALID_NUMBER_OF_ARGUMENTS, expectedNumberOfParameters, list.size())
			);
		}
	}

	public static void validateArgumentsCountTill(List<String> list, int expectedNumberOfParameters) {
		if (list.size() > expectedNumberOfParameters || list.size() == 0) {
			throw new IllegalArgumentException(
					format(INVALID_NUMBER_OF_ARGUMENTS_TILL, 1, expectedNumberOfParameters, list.size())
			);
		}
	}

	public static void validateArgumentsCountMin(List<String> list, int expectedNumberOfParameters) {
		if (list.size() < expectedNumberOfParameters) {
			throw new IllegalArgumentException(
					format(INVALID_NUMBER_OF_ARGUMENTS_MIN, expectedNumberOfParameters, list.size())
			);
		}
	}

	public static void validateArgumentCountRange(List<String> list, int expectedMinNumberOfParameters, int expectedMaxNumberOfParameters) {
		if (list.size() > expectedMaxNumberOfParameters || list.size() < expectedMinNumberOfParameters) {
			throw new IllegalArgumentException(
					format(INVALID_NUMBER_OF_ARGUMENTS_TILL, expectedMinNumberOfParameters, expectedMaxNumberOfParameters, list.size())
			);
		}
	}

}
