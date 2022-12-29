package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.tasks.contracts.Status;
import com.telerikacademy.tms.models.tasks.contracts.Story;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SizeType;
import com.telerikacademy.tms.models.tasks.enums.StoryStatus;

import java.util.List;

import static com.telerikacademy.tms.utils.ParsingHelpers.*;
import static com.telerikacademy.tms.utils.ValidationHelpers.validateArgumentsCount;
import static java.lang.String.format;

public class ChangeStory implements Command {
	private static final int EXPECTED_NUMBER_PARAMETERS = 3;
	private static final String INVALID_CHANGE_COMMAND = "Invalid command for change provided. Use: 'status', 'priority' or 'size'.";
	private static final String CHANGE_TASK_SUCCESSFUL = "%s for %s with ID -> [%d] was changed to {%s}.";
	private static final String STORY = Story.class.getSimpleName();
	private static final String STATUS = Status.class.getSimpleName();
	private static final String PRIORITY = PriorityType.class.getSimpleName().substring(0, PriorityType.class.getSimpleName().length() - 4);
	private static final String SIZE = SizeType.class.getSimpleName().substring(0, SizeType.class.getSimpleName().length() - 4);
	private final TaskManagementRepository repository;

	public ChangeStory(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		int id = tryParseInt(parameters.get(0));
		String typeOfChange = parameters.get(1).toLowerCase();
		return changeStory(parameters, id, typeOfChange);
	}

	private String changeStory(List<String> parameters, int id, String typeOfChange) {
		Story story = repository.findTaskById(repository.getStories(), id, "Story");

		try {
			switch (typeOfChange) {
				case "status":
					StoryStatus storyStatus = tryParseEnum(parameters.get(2), StoryStatus.class);
					story.setStatus(storyStatus);
					return format(CHANGE_TASK_SUCCESSFUL, STATUS, STORY, id, storyStatus);
				case "priority":
					PriorityType priorityType = tryParseEnum(parameters.get(2), PriorityType.class);
					story.setPriority(priorityType);
					return format(CHANGE_TASK_SUCCESSFUL, PRIORITY, STORY, id, priorityType);
				case "size":
					SizeType sizeType = tryParseEnum(parameters.get(2), SizeType.class);
					story.setSize(sizeType);
					return format(CHANGE_TASK_SUCCESSFUL, SIZE, STORY, id, sizeType);
				default:
					throw new UnsupportedOperationException(INVALID_CHANGE_COMMAND);
			}
		} catch (InvalidUserInputException e) {
			throw new InvalidUserInputException(SAME_PARAMETERS_PASSED);
		}
	}
}
