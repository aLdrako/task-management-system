package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.UserImpl;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Bug;
import com.telerikacademy.tms.models.tasks.contracts.Task;
import com.telerikacademy.tms.models.tasks.enums.BugStatus;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.Rating;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;
import com.telerikacademy.tms.utils.FilterHelpers;
import com.telerikacademy.tms.utils.ListingHelpers;
import com.telerikacademy.tms.utils.ParsingHelpers;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.telerikacademy.tms.utils.FilterHelpers.filterByStatus;
import static com.telerikacademy.tms.utils.ValidationHelpers.validateArgumentsSorting;
import static com.telerikacademy.tms.utils.ValidationHelpers.validateFilteringAndSortingParameters;
import static java.lang.String.format;


public class ListAllBugs implements Command {
    public static final String INVALID_FILTER_OPTION_MESSAGE = "Invalid filter option. You can filter the bugs only by status or assignee.";
    public static final String INVALID_SORT_OPTION_MESSAGE = "Invalid sort option. You can sort the bugs only by title/severity/priority.";
    private final TaskManagementRepository repository;

	public ListAllBugs(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
        validateFilteringAndSortingParameters(parameters);

        List<Bug> bugs = listWithBugs();
		bugs = filterBugs(parameters, bugs);
        sortBugs(parameters, bugs);
		return ListingHelpers.elementsToString(bugs);
	}

    private void sortBugs(List<String> parameters, List<Bug> bugs) {
        if (parameters.stream().anyMatch(value -> value.equalsIgnoreCase("sortByTitle"))) {
            Collections.sort(bugs);
        } else if (parameters.stream().anyMatch(value -> value.equalsIgnoreCase("sortByPriority"))) {
            bugs.sort(Comparator.comparing(Bug::getPriority));
        } else if (parameters.stream().anyMatch(value -> value.equalsIgnoreCase("sortBySeverity"))) {
            bugs.sort(Comparator.comparing(Bug::getSeverity));
        } else if (parameters.stream().anyMatch(value -> value.toLowerCase().contains("sortby"))) {
            throw new InvalidUserInputException(INVALID_SORT_OPTION_MESSAGE);
        }
    }

    private List<Bug> filterBugs(List<String> parameters, List<Bug> bugs) {
        if (parameters.get(0).equalsIgnoreCase("filterByStatus")) {
            return filterByStatus(parameters.get(1), bugs, BugStatus.class);
        } else if (parameters.get(0).equalsIgnoreCase("filterByAssignee")) {
            return FilterHelpers.filterByAssignee(parameters.get(1), bugs, repository);
        } else if (parameters.get(0).equalsIgnoreCase("filterByStatusAndAssignee")) {
            bugs = filterByStatus(parameters.get(1), bugs, BugStatus.class);
            return FilterHelpers.filterByAssignee(parameters.get(2), bugs, repository);
        } else if (parameters.stream().anyMatch(value -> value.toLowerCase().contains("filterby"))) {
            throw new InvalidUserInputException(INVALID_FILTER_OPTION_MESSAGE);
        }
        return bugs;
    }

    private List<Bug> listWithBugs() {
        return repository.getTasks().stream()
                .filter(task -> task instanceof Bug)
                .map(Bug.class::cast)
                .collect(Collectors.toList());
	}
}
