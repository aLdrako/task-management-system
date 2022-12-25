package com.telerikacademy.tms.utils;

public class ParsingHelpers {
	public static final String CHANGE_TASK_SUCCESSFUL = "%s for %s with ID %d was changed to %s.";
	public static final String INVALID_TASK_ID_IN_CATEGORY = "Provided task with ID %d does not belong to %s category!";
	public static final String SAME_PARAMETERS_PASSED = "Same value passed. Nothing was changed!";
	private static final String NO_SUCH_ENUM = "There is no value (%s) in %s.";
	private static final String INVALID_NUMBER_INPUT = "Invalid input. Expected a number!";

	public static int tryParseInt(String valueToParse) {
		try {
			return Integer.parseInt(valueToParse);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(INVALID_NUMBER_INPUT);
		}
	}

	public static <E extends Enum<E>> E tryParseEnum(String valueToParse, Class<E> type) {
		try {
			return Enum.valueOf(type, valueToParse.replace(" ", "_").toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(String.format(NO_SUCH_ENUM, valueToParse, type.getSimpleName()));
		}
	}

	public static String convertDigitToWord(String digit) {
		switch (digit) {
			case "1":
				return "one";
			case "2":
				return "two";
			case "3":
				return "three";
			case "4":
				return "four";
			case "5":
				return "five";
			case "6":
				return "six";
			case "7":
				return "seven";
			case "8":
				return "eight";
			case "9":
				return "nine";
			case "10":
				return "ten";
			default:
				return String.format("'%s', use numbers from 1 to 10", digit);
		}
	}
}
