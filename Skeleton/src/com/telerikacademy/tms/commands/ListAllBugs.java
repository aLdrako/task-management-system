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
import com.telerikacademy.tms.utils.ListingHelpers;
import com.telerikacademy.tms.utils.ParsingHelpers;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.telerikacademy.tms.utils.ValidationHelpers.validateArgumentsSorting;
import static java.lang.String.format;


public class ListAllBugs implements Command {
    public static final String INVALID_FILTER_OPTION_MESSAGE = "Invalid filter option. You can filter the bugs only by status or assignee.";
    public static final String INVALID_SORT_OPTION_MESSAGE = "Invalid sort option. You can sort the bugs only by title/severity/priority.";
    public static final String INVALID_ARGUMENTS_AFTER_SORT_MESSAGE = "You can't have arguments after '%s'." +
            "If you wish to filter the list, you need to do it before you sort";
    private final TaskManagementRepository repository;

	public ListAllBugs(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
        Bug bug = repository.createBug("Very bad bug", "Some bad bug here", PriorityType.HIGH, SeverityType.MAJOR);
        Bug bug1 =repository.createBug("Aaaaaaaaaaaaa", "Some bad bug here", PriorityType.MEDIUM, SeverityType.CRITICAL);
        repository.createFeedback("Good Feedback sort", "Some good feedback here", Rating.NINE);
        User user = repository.createUser("Ivancho");
        bug1.setAssignee(user);
        bug.setAssignee(user);
        ValidationHelpers.validateFilteringAndSortingParameters(parameters);
        List<Bug> bugs = listWithBugs();
		bugs = filterBugs(parameters, bugs);
        sortBugs(parameters, bugs);
		return ListingHelpers.elementsToString(bugs);
	}

    private void sortBugs(List<String> parameters, List<Bug> bugs) {
        if (!parameters.get(0).toLowerCase().contains("sortby") && !parameters.get(parameters.size() - 1).toLowerCase()
                .contains("sortby")) {
            return;
        }
        if (parameters.stream().anyMatch(value -> value.equalsIgnoreCase("sortByTitle"))) {
            Collections.sort(bugs);
            return;
        } else if (parameters.stream().anyMatch(value -> value.equalsIgnoreCase("sortByPriority"))) {
            bugs.sort(Comparator.comparing(Bug::getPriority));
            return;
        } else if (parameters.stream().anyMatch(value -> value.equalsIgnoreCase("sortBySeverity"))) {
            bugs.sort(Comparator.comparing(Bug::getSeverity));
            return;
        }
        throw new InvalidUserInputException(INVALID_SORT_OPTION_MESSAGE);
    }

    private List<Bug> filterBugs(List<String> parameters, List<Bug> bugs) {
        if (!parameters.get(0).toLowerCase().contains("filter")){
            return bugs;
        }
        if (parameters.get(0).equalsIgnoreCase("filterByStatus")) {
            BugStatus status = ParsingHelpers.tryParseEnum(parameters.get(1), BugStatus.class);
            return bugs.stream().filter(bug -> bug.getStatus() == status).collect(Collectors.toList());
        } else if (parameters.get(0).equalsIgnoreCase("filterByAssignee")) {
            User user = repository.findElementByName(repository.getUsers(), parameters.get(1));
            return bugs.stream()
                    .filter(bug -> bug.getAssignee() == user)
                    .collect(Collectors.toList());
        } else if (parameters.get(0).equalsIgnoreCase("filterByStatusAndAssignee")) {
            BugStatus status = ParsingHelpers.tryParseEnum(parameters.get(1), BugStatus.class);
            User user = repository.findElementByName(repository.getUsers(), parameters.get(2));
            return bugs.stream()
                    .filter(bug -> bug.getStatus() == status)
                    .filter(bug -> bug.getAssignee() == user)
                    .collect(Collectors.toList());
        }
        throw new InvalidUserInputException(INVALID_FILTER_OPTION_MESSAGE);
    }

    private List<Bug> listWithBugs() {

        return repository.getTasks().stream()
                .filter(task -> task instanceof Bug)
                .map(Bug.class::cast)
                .collect(Collectors.toList());
	}
}
