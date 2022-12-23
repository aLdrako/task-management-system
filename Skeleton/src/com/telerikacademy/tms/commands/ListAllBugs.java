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
import com.telerikacademy.tms.utils.ParsingHelpers;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class ListAllBugs implements Command {
	public static final int EXPECTED_MAX_NUMBER_PARAMETERS = 3;
    public static final String INVALID_FILTER_OPTION_MESSAGE = "Invalid filter option. You can filter the bugs only by status or assignee.";
    private final TaskManagementRepository repository;

	public ListAllBugs(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		ValidationHelpers.validateArgumentsCountTill(parameters, EXPECTED_MAX_NUMBER_PARAMETERS);
        repository.createBug("Verybad bug", "Some bad bug here", PriorityType.HIGH, SeverityType.CRITICAL);
        repository.createBug("Very bad bug", "Some bad bug here", PriorityType.MEDIUM, SeverityType.MAJOR);
        List<Bug> bugs = listWithBugs();
        System.out.println(bugs);
		bugs = filterBugs(parameters, bugs);
        bugs = sortBugs(parameters, bugs);
        System.out.println(bugs);
		return null;
	}

    private List<Bug> sortBugs(List<String> parameters, List<Bug> bugs) {
        if (parameters.stream().anyMatch(value -> value.equalsIgnoreCase("sortByTitle"))) {
            Collections.sort(bugs);
            return bugs;
        } else if (parameters.stream().anyMatch(value -> value.equalsIgnoreCase("sortByPriority"))) {
            bugs.sort(Comparator.comparing(Bug::getPriority));
            return bugs;
        } else if (parameters.stream().anyMatch(value -> value.equalsIgnoreCase("sortBySeverity"))) {
            bugs.sort(Comparator.comparing(Bug::getSeverity));
            return bugs;
        }
        return bugs;
    }

    private List<Bug> filterBugs(List<String> parameters, List<Bug> bugs) {
        if (!parameters.get(0).contains("filter")){
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
        }
        throw new InvalidUserInputException(INVALID_FILTER_OPTION_MESSAGE);
    }

    private List<Bug> listWithBugs() {
		List<Bug> list = new ArrayList<>();
		for (Task task : repository.getTasks()) {
			if (task instanceof Bug) {
				list.add((Bug) task);
			}
		}
		return list;
	}
}
