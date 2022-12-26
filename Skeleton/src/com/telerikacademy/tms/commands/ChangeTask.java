package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.tasks.contracts.*;
import com.telerikacademy.tms.models.tasks.enums.*;

import java.util.List;

import static com.telerikacademy.tms.utils.ParsingHelpers.*;
import static com.telerikacademy.tms.utils.ValidationHelpers.validateArgumentsCount;
import static java.lang.String.format;

public class ChangeTask implements Command {
	private static final int EXPECTED_NUMBER_PARAMETERS = 3;
	private static final String INVALID_CHANGE_COMMAND = "Invalid command for change provided. Use: 'status', 'priority', 'severity', 'size' or 'rating'.";
	private static final String WRONG_SETTING_SPECIFIED = "%s does not have '%s' setting!";
	private static final String CHANGE_TASK_SUCCESSFUL = "%s for %s with ID %d was changed to %s.";
	private static final String STATUS = Status.class.getSimpleName();
	private static final String PRIORITY = PriorityType.class.getSimpleName().substring(0, PriorityType.class.getSimpleName().length() - 4);
	private static final String SEVERITY = SeverityType.class.getSimpleName().substring(0, SeverityType.class.getSimpleName().length() - 4);
	private static final String SIZE = SizeType.class.getSimpleName().substring(0, SizeType.class.getSimpleName().length() - 4);
	private static final String RATING = Rating.class.getSimpleName();

	private final TaskManagementRepository repository;

	public ChangeTask(TaskManagementRepository taskManagementRepository) {
		this.repository = taskManagementRepository;
	}

	@Override
	public String execute(List<String> parameters) {
		validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		int id = tryParseInt(parameters.get(0));
		String typeOfChange = parameters.get(1).toLowerCase();
		return changeTask(parameters, id, typeOfChange);
	}

	private String changeTask(List<String> parameters, int id, String typeOfChange) {
		Task task = repository.findElementById(repository.getTasks(), id);

		try {
			switch (typeOfChange) {
				case "status":
					return getStatusString(parameters, id, task);
				case "priority":
					return getPriorityString(parameters, id, task);
				case "severity":
					return getSeverityString(parameters, id, task);
				case "size":
					return getSizeString(parameters, id, task);
				case "rating":
					return getRatingString(parameters, id, task);
				default:
					return INVALID_CHANGE_COMMAND;
			}
		} catch (InvalidUserInputException e) {
			throw new InvalidUserInputException(SAME_PARAMETERS_PASSED);
		}

	}

	private static String getRatingString(List<String> parameters, int id, Task task) {
		if (task.getTaskType().equals(TaskType.BUG)) {
			return format(WRONG_SETTING_SPECIFIED, task.getTaskType(), RATING);
		} else if (task.getTaskType().equals(TaskType.STORY)) {
			return format(WRONG_SETTING_SPECIFIED, task.getTaskType(), RATING);
		} else {
			Feedback feedback = (Feedback) task;
			Rating rating = tryParseEnum(convertDigitToWord(parameters.get(2)), Rating.class);
			feedback.setRating(rating);
			return format(CHANGE_TASK_SUCCESSFUL, RATING, task.getTaskType(), id, rating);
		}
	}

	private static String getSizeString(List<String> parameters, int id, Task task) {
		if (task.getTaskType().equals(TaskType.BUG)) {
			return format(WRONG_SETTING_SPECIFIED, task.getTaskType(), SIZE);
		} else if (task.getTaskType().equals(TaskType.STORY)) {
			Story story = (Story) task;
			SizeType sizeType = tryParseEnum(parameters.get(2), SizeType.class);
			story.setSize(sizeType);
			return format(CHANGE_TASK_SUCCESSFUL, SIZE, task.getTaskType(), id, sizeType);
		} else {
			return format(WRONG_SETTING_SPECIFIED, task.getTaskType(), SIZE);
		}
	}

	private static String getSeverityString(List<String> parameters, int id, Task task) {
		if (task.getTaskType().equals(TaskType.BUG)) {
			Bug bug = (Bug) task;
			SeverityType severityType = tryParseEnum(parameters.get(2), SeverityType.class);
			bug.setSeverity(severityType);
			return format(CHANGE_TASK_SUCCESSFUL, SEVERITY, task.getTaskType(), id, severityType);
		} else if (task.getTaskType().equals(TaskType.STORY)) {
			return format(WRONG_SETTING_SPECIFIED, task.getTaskType(), SEVERITY);
		} else {
			return format(WRONG_SETTING_SPECIFIED, task.getTaskType(), SEVERITY);
		}
	}

	private static String getPriorityString(List<String> parameters, int id, Task task) {
		if (task.getTaskType().equals(TaskType.BUG)) {
			Bug bug = (Bug) task;
			PriorityType priorityType = tryParseEnum(parameters.get(2), PriorityType.class);
			bug.setPriority(priorityType);
			return format(CHANGE_TASK_SUCCESSFUL, PRIORITY, task.getTaskType(), id, priorityType);
		} else if (task.getTaskType().equals(TaskType.STORY)) {
			Story story = (Story) task;
			PriorityType priorityType = tryParseEnum(parameters.get(2), PriorityType.class);
			story.setPriority(priorityType);
			return format(CHANGE_TASK_SUCCESSFUL, PRIORITY, task.getTaskType(), id, priorityType);
		} else {
			return format(WRONG_SETTING_SPECIFIED, task.getTaskType(), PRIORITY);
		}
	}

	private static String getStatusString(List<String> parameters, int id, Task task) {
		if (task.getTaskType().equals(TaskType.BUG)) {
			Bug bug = (Bug) task;
			BugStatus bugStatus = tryParseEnum(parameters.get(2), BugStatus.class);
			bug.setStatus(bugStatus);
			return format(CHANGE_TASK_SUCCESSFUL, STATUS, task.getTaskType(), id, bugStatus);
		} else if (task.getTaskType().equals(TaskType.STORY)) {
			Story story = (Story) task;
			StoryStatus storyStatus = tryParseEnum(parameters.get(2), StoryStatus.class);
			story.setStatus(storyStatus);
			return format(CHANGE_TASK_SUCCESSFUL, STATUS, task.getTaskType(), id, storyStatus);
		} else {
			Feedback feedback = (Feedback) task;
			FeedbackStatus feedbackStatus = tryParseEnum(parameters.get(2), FeedbackStatus.class);
			feedback.setStatus(feedbackStatus);
			return format(CHANGE_TASK_SUCCESSFUL, STATUS, task.getTaskType(), id, feedbackStatus);
		}
	}
}