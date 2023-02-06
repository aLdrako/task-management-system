package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.tasks.contracts.Bug;
import com.telerikacademy.tms.models.tasks.enums.BugStatus;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.telerikacademy.tms.utils.FilterHelpers.filterByAssignee;
import static com.telerikacademy.tms.utils.FilterHelpers.filterByStatus;
import static com.telerikacademy.tms.utils.ListingHelpers.elementsToString;
import static com.telerikacademy.tms.utils.ListingHelpers.listingCommandsSubHeader;
import static com.telerikacademy.tms.utils.ValidationHelpers.ZERO_PARAMETERS;
import static com.telerikacademy.tms.utils.ValidationHelpers.validateFilteringAndSortingParameters;
import static java.lang.String.format;


public class ListAllBugs implements Command {
    private static final String INVALID_COUNT_PARAMETER = "Invalid parameter count.";
    private static final String INVALID_FILTER_OPTION_MESSAGE = "Invalid filter option. You can filter the bugs only by status or assignee.";
    private static final String INVALID_SORT_OPTION_MESSAGE = "Invalid sort option. You can sort the bugs only by title/severity/priority.";
    private static final String LISTING_HEADER = "LIST ALL BUGS %s %n%s";
    private final TaskManagementRepository repository;

    public ListAllBugs(TaskManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        List<Bug> bugs = repository.getBugs();
        if (parameters.size() == ZERO_PARAMETERS) {
            return format(LISTING_HEADER, listingCommandsSubHeader(parameters), elementsToString(bugs));
        }
        validateFilteringAndSortingParameters(parameters);
        bugs = filterBugs(parameters, bugs);
        sortBugs(parameters, bugs);
        return format(LISTING_HEADER, listingCommandsSubHeader(parameters), elementsToString(bugs));
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
        try {
            if (parameters.get(0).equalsIgnoreCase("filterByStatus")) {
                return filterByStatus(parameters.get(1), bugs, BugStatus.class);
            } else if (parameters.get(0).equalsIgnoreCase("filterByAssignee")) {
                return filterByAssignee(parameters.get(1), bugs, repository);
            } else if (parameters.get(0).equalsIgnoreCase("filterByStatusAndAssignee")) {
                bugs = filterByStatus(parameters.get(1), bugs, BugStatus.class);
                return filterByAssignee(parameters.get(2), bugs, repository);
            } else if (parameters.get(0).toLowerCase().contains("filterby")) {
                throw new InvalidUserInputException(INVALID_FILTER_OPTION_MESSAGE);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidUserInputException(INVALID_COUNT_PARAMETER);
        }
        return bugs;
    }
}
