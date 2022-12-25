package com.telerikacademy.tms.utils;

import com.telerikacademy.tms.models.tasks.contracts.Historiable;

import java.util.List;
import java.util.stream.Collectors;

public class ListingHelpers {
	public static String EMPTY_LIST_MESSAGE = "=== EMPTY LIST ===";

	public static <T extends Historiable> String elementsToString(List<T> elements) {
		if (elements.isEmpty()) {
			return EMPTY_LIST_MESSAGE;
		}
		return elements.stream()
				.map(Object::toString)
				.collect(Collectors.joining("\n===============\n"));
	}

}
