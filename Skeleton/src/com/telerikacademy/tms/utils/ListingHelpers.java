package com.telerikacademy.tms.utils;

import com.telerikacademy.tms.models.tasks.contracts.Historiable;

import java.util.List;
import java.util.stream.Collectors;

public class ListingHelpers {

	public static <T extends Historiable> String elementsToString(List<T> elements) {
		return elements.stream()
				.map(Object::toString)
				.collect(Collectors.joining("\n===============\n"));
	}
}
