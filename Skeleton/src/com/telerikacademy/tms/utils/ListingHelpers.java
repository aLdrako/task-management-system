package com.telerikacademy.tms.utils;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;


public class ListingHelpers {
	public static String EMPTY_LIST_MESSAGE = "=== EMPTY LIST ===";
	public static final String ACTIVITY_HISTORY_HEADER = "<<< %s %s's ACTIVITY HISTORY >>>";

	public static <T> String elementsToString(List<T> elements) {
		if (elements.isEmpty()) {
			return EMPTY_LIST_MESSAGE;
		}
		return elements.stream()
				.map(Object::toString)
				.collect(Collectors.joining("\n===============\n"));
	}

	public static <T> String activityListing(List<T> elements) {
		return elements.stream()
				.map(Object::toString)
				.collect(Collectors.joining("\n"));
	}

	public static String listingCommandsSubHeader(List<String> parameters) {

	return parameters.stream()
			.reduce("", (acc, comb) -> {
				if (comb.toLowerCase().contains("filterby")) {
					acc += format("%n\t-> %s: ", comb);
				} else if (comb.toLowerCase().contains("sortby")) {
					acc += format("%n\t-> %s ", comb);
				} else {
					acc += format("\"%s\" ", comb);
				}
				return acc;
			});
		}
}
