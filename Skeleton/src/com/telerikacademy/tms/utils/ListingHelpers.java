package com.telerikacademy.tms.utils;

import java.util.List;
import java.util.stream.Collectors;


public class ListingHelpers {
	public static String EMPTY_LIST_MESSAGE = "=== EMPTY LIST ===";

	public static <T> String elementsToString(List<T> elements) {
		if (elements.isEmpty()) {
			return EMPTY_LIST_MESSAGE;
		}
		return elements.stream()
				.map(Object::toString)
				.collect(Collectors.joining("\n===============\n"));
	}

	public static String listingCommandsSubHeader(List<String> parameters) {
		return parameters.stream()
				.reduce("", (acc, comb) -> {
					if (!comb.toLowerCase().contains("by")) {
						acc += String.format("'%s' ", comb) + System.lineSeparator();
					} else {
						acc += comb + ": " + System.lineSeparator();
					}
					return acc;
				});
	}

}
