package com.telerikacademy.tms.utils;

public class ParsingHelpers {
	public static final String CHANGE_TASK_SUCCESSFUL = "%s for %s with ID %d was changed to %s";
	public static final String INVALID_TASK_ID_IN_CATEGORY = "Provided task with ID %d does not belong to %s category";
	private static final String NO_SUCH_ENUM = "There is no value (%s) in %s.";
	private static final String INVALID_NUMBER_INPUT = "Invalid input. Expected a number.";


	public static double tryParseDouble(String valueToParse, String errorMessage) {
		try {
			return Double.parseDouble(valueToParse);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

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
}
