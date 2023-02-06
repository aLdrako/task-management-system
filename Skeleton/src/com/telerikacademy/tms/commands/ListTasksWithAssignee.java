package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.tasks.contracts.Assignable;
import com.telerikacademy.tms.models.tasks.enums.BugStatus;
import com.telerikacademy.tms.models.tasks.enums.StoryStatus;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.telerikacademy.tms.utils.FilterHelpers.filterByAssignee;
import static com.telerikacademy.tms.utils.FilterHelpers.filterByStatus;
import static com.telerikacademy.tms.utils.ListingHelpers.elementsToString;
import static com.telerikacademy.tms.utils.ListingHelpers.listingCommandsSubHeader;
import static com.telerikacademy.tms.utils.ValidationHelpers.validateFilteringAndSortingParameters;
import static java.lang.String.format;

public class ListTasksWithAssignee implements Command {
    private static final String LISTING_HEADER = "LIST TASKS WITH ASSIGNEE %s %n%s";
    private static final String INVALID_FILTER_OPTION_MESSAGE = "Invalid filter option. You can filter the tasks with assignees only by status/assignee.";
    private static final String INVALID_SORT_OPTION_MESSAGE = "Invalid sort option. You can sort the assignable tasks only by title.";
    private static final String INVALID_STATUS = "There is not status '%s' in the assignable tasks";
    public static final String INVALID_COUNT_PARAMETER = "Invalid parameter count.";
    private final TaskManagementRepository repository;

    public ListTasksWithAssignee(TaskManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        List<Assignable> assignableTasks = repository.getAssignableTasks().stream()
                .filter(task -> !task.getAssignee().getName().equalsIgnoreCase("Unassigned"))
                .collect(Collectors.toList());
        if (parameters.size() == 0) {
            return format(LISTING_HEADER, listingCommandsSubHeader(parameters),
                    elementsToString(assignableTasks));
        }
        validateFilteringAndSortingParameters(parameters);
        assignableTasks = filterTasks(parameters, assignableTasks);
        sortTasks(parameters, assignableTasks);
        return format(LISTING_HEADER, listingCommandsSubHeader(parameters), elementsToString(assignableTasks));
    }

    private void sortTasks(List<String> parameters, List<Assignable> assignableTasks) {
        if (parameters.stream().anyMatch(value -> value.equalsIgnoreCase("sortByTitle"))) {
            Collections.sort(assignableTasks);
        } else if (parameters.stream().anyMatch(value -> value.toLowerCase().contains("sortby"))) {
            throw new InvalidUserInputException(INVALID_SORT_OPTION_MESSAGE);
        }
    }

    private List<Assignable> filterTasks(List<String> parameters, List<Assignable> assignableTasks) {
        try {
            if (parameters.get(0).equalsIgnoreCase("filterByStatus")) {
                return filterByStatusAssignable(parameters.get(1), assignableTasks);
            } else if (parameters.get(0).equalsIgnoreCase("filterByAssignee")) {
                return filterByAssignee(parameters.get(1), assignableTasks, repository);
            } else if (parameters.get(0).equalsIgnoreCase("filterByStatusAndAssignee")) {
                assignableTasks = filterByStatusAssignable(parameters.get(1), assignableTasks);
                return filterByAssignee(parameters.get(2), assignableTasks, repository);
            } else if (parameters.get(0).toLowerCase().contains("filterby")) {
                throw new InvalidUserInputException(INVALID_FILTER_OPTION_MESSAGE);
            }
        } catch (IndexOutOfBoundsException ex) {
            throw new InvalidUserInputException(INVALID_COUNT_PARAMETER);
        }
        return assignableTasks;
    }

    public List<Assignable> filterByStatusAssignable(String parameter, List<Assignable> list) {
        try {
            return filterByStatus(parameter, list, BugStatus.class);
        } catch (IllegalArgumentException ex) {
            try {
                return filterByStatus(parameter, list, StoryStatus.class);
            } catch (IllegalArgumentException e) {
                throw new InvalidUserInputException(format(INVALID_STATUS, parameter));
            }
        }
    }
}