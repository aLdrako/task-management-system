package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.ElementNotFoundException;
import com.telerikacademy.tms.models.UserImpl;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Assignable;
import com.telerikacademy.tms.models.tasks.contracts.Task;

import java.util.List;

import static com.telerikacademy.tms.utils.ParsingHelpers.INVALID_TASK_ID_IN_CATEGORY;
import static com.telerikacademy.tms.utils.ParsingHelpers.tryParseInt;
import static com.telerikacademy.tms.utils.ValidationHelpers.validateArgumentsCount;
import static java.lang.String.format;

public class UnassignTask implements Command {
	private static final String TASK_UNASSIGNED_SUCCESSFUL = "Task with ID %s was unassigned from user %s.";
	private static final int EXPECTED_NUMBER_PARAMETERS = 2;
	private final TaskManagementRepository repository;

	public UnassignTask(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		int id = tryParseInt(parameters.get(0));
		String userName = parameters.get(1);

		return unassignTask(id, userName);
	}

	private String unassignTask(int id, String userName) {
		Assignable assignableTask;
		try {
			assignableTask = (Assignable) repository.findElementById(repository.getTasks(), id);
			User user = repository.findElementByName(repository.getUsers(), userName);
			user.unAssignTask((Task) assignableTask);
			User unassignedtUser = new UserImpl("Unassigned");
			assignableTask.setAssignee(unassignedtUser);
		} catch (ClassCastException e) {
			throw new ClassCastException(format(INVALID_TASK_ID_IN_CATEGORY, id, Assignable.class.getSimpleName()));
		} catch (ElementNotFoundException e) {
			throw new ElementNotFoundException(e.getMessage());
		}

		return format(TASK_UNASSIGNED_SUCCESSFUL, id, userName);
	}
}
