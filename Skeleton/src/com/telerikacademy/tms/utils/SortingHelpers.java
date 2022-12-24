package com.telerikacademy.tms.utils;

import com.telerikacademy.tms.models.tasks.contracts.Task;

import java.util.Collections;
import java.util.List;

public class SortingHelpers {

    public static <T extends Task> void sortingByTitle(List<T> list, List<String> parameters) {
        if (parameters.stream().anyMatch(value -> value.equalsIgnoreCase("sortByTitle"))) {
            Collections.sort(list);
        }
    }
}
