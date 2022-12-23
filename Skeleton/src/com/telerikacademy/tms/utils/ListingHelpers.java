package com.telerikacademy.tms.utils;

import com.telerikacademy.tms.models.tasks.contracts.Historiable;

import java.util.List;

public class ListingHelpers {

    public <T extends Historiable> String elementsToString(List<T> elements) {
        return elements.stream()
                .map(Object::toString)
                .reduce("", (acc, comb) -> acc + comb + System.lineSeparator());
    }
}
