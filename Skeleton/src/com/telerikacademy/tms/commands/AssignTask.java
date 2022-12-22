package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.ElementNotFoundException;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Assignable;
import com.telerikacademy.tms.models.tasks.contracts.Task;

import java.util.List;

import static com.telerikacademy.tms.utils.ParsingHelpers.INVALID_TASK_ID_IN_CATEGORY;
import static com.telerikacademy.tms.utils.ParsingHelpers.tryParseInt;
import static com.telerikacademy.tms.utils.ValidationHelpers.validateArgumentsCount;
import static java.lang.String.format;

public class AssignTask implements Command {
	private static final String TASK_ASSIGNED_SUCCESSFUL = "Task with ID %s was assigned to user %s.";
	private static final int EXPECTED_NUMBER_PARAMETERS = 2;
	private final TaskManagementRepository repository;

	public AssignTask(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		//TODO to remove after test and implementation of all commands
//		User user = repository.createUser("Alexa");
//		repository.createBug("Very bad bug", "Some bad bug here", PriorityType.MEDIUM, SeverityType.CRITICAL);
//		repository.createFeedback("Good Feedback", "Some good feedback here", Rating.NINE);
//		repository.createStory("Good Story", "Some good story here", PriorityType.LOW, SizeType.MEDIUM);
		validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		int id = tryParseInt(parameters.get(0));
		String userName = parameters.get(1);

		return assignTask(id, userName);
	}

	private String assignTask(int id, String userName) {
		Assignable assignableTask;
		try {
			assignableTask = (Assignable) repository.findElementById(repository.getTasks(), id);
			User user = repository.findElementByName(repository.getUsers(), userName);
			user.assignTask((Task) assignableTask);
			assignableTask.setAssignee(user);
		} catch (ClassCastException e) {
			throw new ClassCastException(format(INVALID_TASK_ID_IN_CATEGORY, id, Assignable.class.getSimpleName()));
		} catch (ElementNotFoundException e) {
			throw new ElementNotFoundException(e.getMessage());
		}

		return format(TASK_ASSIGNED_SUCCESSFUL, id, userName);
	}
}
