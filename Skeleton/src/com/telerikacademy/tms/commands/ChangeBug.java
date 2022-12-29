package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.tasks.contracts.Bug;
import com.telerikacademy.tms.models.tasks.contracts.Status;
import com.telerikacademy.tms.models.tasks.enums.BugStatus;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;

import java.util.List;

import static com.telerikacademy.tms.utils.ParsingHelpers.*;
import static com.telerikacademy.tms.utils.ValidationHelpers.validateArgumentsCount;
import static java.lang.String.format;

public class ChangeBug implements Command {
	private static final int EXPECTED_NUMBER_PARAMETERS = 3;
	private static final String INVALID_CHANGE_COMMAND = "Invalid command for change provided. Use: 'status', 'priority' or 'severity'.";
	private static final String CHANGE_TASK_SUCCESSFUL = "%s for %s with ID -> [%d] was changed to {%s}.";
	private static final String BUG = Bug.class.getSimpleName();
	private static final String STATUS = Status.class.getSimpleName();
	private static final String PRIORITY = PriorityType.class.getSimpleName().substring(0, PriorityType.class.getSimpleName().length() - 4);
	private static final String SEVERITY = SeverityType.class.getSimpleName().substring(0, SeverityType.class.getSimpleName().length() - 4);
	private final TaskManagementRepository repository;

	public ChangeBug(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		int id = tryParseInt(parameters.get(0));
		String typeOfChange = parameters.get(1).toLowerCase();
		return changeBug(parameters, id, typeOfChange);
	}

	private String changeBug(List<String> parameters, int id, String typeOfChange) {
		Bug bug = repository.findTaskById(repository.getBugs(), id, "Bug");

		try {
			switch (typeOfChange) {
				case "status":
					BugStatus bugStatus = tryParseEnum(parameters.get(2), BugStatus.class);
					bug.setStatus(bugStatus);
					return format(CHANGE_TASK_SUCCESSFUL, STATUS, BUG, id, bugStatus);
				case "priority":
					PriorityType priorityType = tryParseEnum(parameters.get(2), PriorityType.class);
					bug.setPriority(priorityType);
					return format(CHANGE_TASK_SUCCESSFUL, PRIORITY, BUG, id, priorityType);
				case "severity":
					SeverityType severityType = tryParseEnum(parameters.get(2), SeverityType.class);
					bug.setSeverity(severityType);
					return format(CHANGE_TASK_SUCCESSFUL, SEVERITY, BUG, id, severityType);
				default:
					throw new UnsupportedOperationException(INVALID_CHANGE_COMMAND);
			}
		} catch (InvalidUserInputException e) {
			throw new InvalidUserInputException(SAME_PARAMETERS_PASSED);
		}
	}
}
