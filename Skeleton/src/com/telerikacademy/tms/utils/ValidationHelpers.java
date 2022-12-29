package com.telerikacademy.tms.utils;

import com.telerikacademy.tms.exceptions.InvalidUserInputException;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class ValidationHelpers {
	private static final String INVALID_COMMAND = "Invalid command input. Tasks can be filtered or sorted.";
	private static final String INVALID_ARGUMENTS_AFTER_SORT_MESSAGE = "You can't have arguments after '%s'." +
			"If you wish to filter the list, you need to do it before you sort";
	private static final String INVALID_NUMBER_OF_ARGUMENTS = "Invalid number of arguments. Expected: %d; received: %d.";
	private static final String INVALID_NUMBER_OF_ARGUMENTS_MIN = "Invalid number of arguments. Expected: at least %d; received: %d.";
	private static final String INVALID_NUMBER_OF_ARGUMENTS_RANGE = "Invalid number of arguments. Expected: at least %d; expected: %d; received: %d.";
	private static final String INVALID_PARAMETER_MESSAGE = "Invalid argument detected.";
	public static final int ZERO_PARAMETERS = 0;
	public static final int EXPECTED_PARAMETERS_MULTIPLE_FILTERING = 4;
	public static final int EXPECTED_PARAMETERS_SINGLE_FILTERING = 3;

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

	public static void validateArgumentsMin(List<String> list, int minExpectedNumberOfParameters) {
		if (list.size() < minExpectedNumberOfParameters) {
			throw new IllegalArgumentException(
					format(INVALID_NUMBER_OF_ARGUMENTS_MIN, minExpectedNumberOfParameters, list.size())
			);
		}
	}

	public static void validateArgumentsCountTill(List<String> list, int expectedNumberOfParameters) {
		if (list.size() > expectedNumberOfParameters || list.size() == 0) {
			throw new IllegalArgumentException(
					format(INVALID_NUMBER_OF_ARGUMENTS_RANGE, 1, expectedNumberOfParameters, list.size())
			);
		}
	}

	/**
	 * In case there is a sorting parameter in the list, this method checks to see if there are parameters after
	 * the "sort" parameter and throws an InvalidUserInputException if true.
	 *
	 * @param list - parameters
	 */
	public static void validateArgumentsSorting(List<String> list) {
		if (list.stream().anyMatch(value -> value.toLowerCase().contains("sortby"))) {
			int index = list.indexOf(list.stream().reduce("", (acc, comb) -> {
				List<String> words = list
						.stream()
						.filter(value -> value.toLowerCase().contains("sortby"))
						.collect(Collectors.toList());
				return words.get(0);
			}));
			if (list.size() - 1 != index) {
				throw new InvalidUserInputException(format(INVALID_ARGUMENTS_AFTER_SORT_MESSAGE, list.get(index)));
			}
		}
	}

	/**
	 * In case there is a filtering parameter in the list.
	 *
	 * @param list - parameters
	 */
	public static void validateArgumentsFiltering(List<String> list) {
		if (list.get(0).toLowerCase().contains("filterby")) {
			int maxParameters;
			if (list.get(0).toLowerCase().contains("and")) {
				validateArgumentsCountTill(list, EXPECTED_PARAMETERS_MULTIPLE_FILTERING);
				maxParameters = 4;
			} else {
				validateArgumentsCountTill(list, EXPECTED_PARAMETERS_SINGLE_FILTERING);
				maxParameters = 3;
			}
			if (list.size() == maxParameters && !list.get(maxParameters - 1).toLowerCase().contains("sortby")) {
				throw new InvalidUserInputException(INVALID_PARAMETER_MESSAGE);
			}
		}
	}

	public static void validateFilteringAndSortingParameters(List<String> list) {
		if (list.stream().noneMatch(value -> value.toLowerCase().contains("sortby") ||
				value.toLowerCase().contains("filterby"))) {
			throw new InvalidUserInputException(INVALID_COMMAND);
		}
		validateArgumentsFiltering(list);
	}
}
