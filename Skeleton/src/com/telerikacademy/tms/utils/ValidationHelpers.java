package com.telerikacademy.tms.utils;

import com.telerikacademy.tms.exceptions.InvalidUserInputException;

import java.util.List;

import static java.lang.String.format;

public class ValidationHelpers {
    private static final String INVALID_COMMAND = "Invalid command input. Tasks can be filtered or sorted.";
    private static final String INVALID_ARGUMENTS_AFTER_SORT_MESSAGE = "You can't have arguments after a 'sort' command." +
            "If you wish to filter the list, you need to do it before you sort";
    private static final String INVALID_NUMBER_OF_ARGUMENTS = "Invalid number of arguments. Expected: %d; received: %d.";
    private static final String INVALID_NUMBER_OF_ARGUMENTS_MIN = "Invalid number of arguments. Expected: at least %d; received: %d.";
    private static final String INVALID_NUMBER_OF_ARGUMENTS_RANGE = "Invalid number of arguments. Expected max parameters: %d; received: %d.";
    private static final String INVALID_PARAMETER_MESSAGE = "Invalid argument detected. You can filter only once and sort the list if you wish.";
    public static final int ZERO_PARAMETERS = 0;
    private static final int EXPECTED_PARAMETERS_MULTIPLE_FILTERING = 4;
    private static final int EXPECTED_PARAMETERS_SINGLE_FILTERING = 3;

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

    public static void validateArgumentsMax(List<String> list, int expectedNumberOfParameters) {
        if (list.size() > expectedNumberOfParameters) {
            throw new IllegalArgumentException(
                    format(INVALID_NUMBER_OF_ARGUMENTS_RANGE, expectedNumberOfParameters, list.size())
            );
        }
    }

    /**
     * In case the first parameter is a sorting parameter, this method checks to see if there are parameters after
     * the "sort" parameter and throws an InvalidUserInputException if true.
     *
     * @param list - parameters
     */
    public static void validateArgumentsSorting(List<String> list) {
        if (list.get(0).toLowerCase().contains("sortby") && list.size() > 1) {
            throw new InvalidUserInputException(INVALID_ARGUMENTS_AFTER_SORT_MESSAGE);
        }
    }

    /**
     * In case there is a filtering parameter in the list.
     *
     * @param list - parameters
     */
    public static void validateArgumentsFiltering(List<String> list) {
        if (list.get(0).toLowerCase().contains("filterby")) {
            int maxParameters = (list.get(0).toLowerCase().contains("and"))
                    ? EXPECTED_PARAMETERS_MULTIPLE_FILTERING : EXPECTED_PARAMETERS_SINGLE_FILTERING;
            validateArgumentsMax(list, maxParameters);

            if (list.size() == maxParameters && !list.get(maxParameters - 1).toLowerCase().contains("sortby")) {
                throw new InvalidUserInputException(INVALID_PARAMETER_MESSAGE);
            }
        }
    }

    public static void validateFilteringAndSortingParameters(List<String> list) {
        if (!list.get(0).toLowerCase().contains("sortby") && !list.get(0).toLowerCase().contains("filterby")) {
            throw new InvalidUserInputException(INVALID_COMMAND);
        }
        validateArgumentsFiltering(list);
        validateArgumentsSorting(list);
    }
}
