package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.tasks.contracts.Bug;
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
	private static final String CHANGE_TASK_SUCCESSFUL = "%s for %s with ID %d was changed to %s.";
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
		Bug bug;
		try {
			bug = (Bug) repository.findElementById(repository.getTasks(), id);
		} catch (ClassCastException e) {
			throw new ClassCastException(format(INVALID_TASK_ID_IN_CATEGORY, id, Bug.class.getSimpleName()));
		}

		try {
			switch (typeOfChange) {
				case "status":
					BugStatus bugStatus = tryParseEnum(parameters.get(2), BugStatus.class);
					if (bug.getStatus().equals(bugStatus)) throw new InvalidUserInputException();
					bug.setStatus(bugStatus);
					return getFormatedString(id, bugStatus, false);
				case "priority":
					PriorityType priorityType = tryParseEnum(parameters.get(2), PriorityType.class);
					if (bug.getPriority().equals(priorityType)) throw new InvalidUserInputException();
					bug.setPriority(priorityType);
					return getFormatedString(id, priorityType, true);
				case "severity":
					SeverityType severityType = tryParseEnum(parameters.get(2), SeverityType.class);
					if (bug.getSeverity().equals(severityType)) throw new InvalidUserInputException();
					bug.setSeverity(severityType);
					return getFormatedString(id, severityType, true);
				default:
					return INVALID_CHANGE_COMMAND;
			}
		} catch (InvalidUserInputException e) {
			throw new InvalidUserInputException(SAME_PARAMETERS_PASSED);
		}
	}

	private static <E extends Enum<E>> String getFormatedString(int id, E changeType, boolean statusOrElse) {
		String statusOrElseString = !statusOrElse
				? changeType.getClass().getInterfaces()[0].getSimpleName()
				: changeType.getClass().getSimpleName().substring(0, changeType.getClass().getSimpleName().length() - 4);

		return format(CHANGE_TASK_SUCCESSFUL, statusOrElseString, Bug.class.getSimpleName(), id, changeType);
	}
}
