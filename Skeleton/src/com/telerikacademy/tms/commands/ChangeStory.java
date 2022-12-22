package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.ElementNotFoundException;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
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
	private final TaskManagementRepository repository;

	public ChangeStory(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		//TODO to remove after test and implementation of all commands
//		repository.createStory("Good Story", "Some good story here", PriorityType.LOW, SizeType.MEDIUM);
		validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		int id = tryParseInt(parameters.get(0));
		String typeOfChange = parameters.get(1);

		return changeStory(parameters, id, typeOfChange);
	}

	private String changeStory(List<String> parameters, int id, String typeOfChange) {
		Story story;
		try {
			story = (Story) repository.findElementById(repository.getTasks(), id);
		} catch (ClassCastException e) {
			throw new ClassCastException(format(INVALID_TASK_ID_IN_CATEGORY, id, Story.class.getSimpleName()));
		} catch (ElementNotFoundException e) {
			throw new ElementNotFoundException(e.getMessage());
		}

		try {
			switch (typeOfChange) {
				case "status":
					StoryStatus storyStatus = tryParseEnum(parameters.get(2), StoryStatus.class);
					if (story.getStatus().equals(storyStatus)) throw new InvalidUserInputException();
					story.setStatus(storyStatus);
					return getFormatedString(id, storyStatus, false);
				case "priority":
					PriorityType priorityType = tryParseEnum(parameters.get(2), PriorityType.class);
					if (story.getPriority().equals(priorityType)) throw new InvalidUserInputException();
					story.setPriority(priorityType);
					return getFormatedString(id, priorityType, true);
				case "size":
					SizeType sizeType = tryParseEnum(parameters.get(2), SizeType.class);
					if (story.getSize().equals(sizeType)) throw new InvalidUserInputException();
					story.setSize(sizeType);
					return getFormatedString(id, sizeType, true);
				default:
					throw new InvalidUserInputException(INVALID_CHANGE_COMMAND);
			}
		} catch (InvalidUserInputException e) {
			throw new InvalidUserInputException(SAME_PARAMETERS_PASSED);
		}
	}

	private static <E extends Enum<E>> String getFormatedString(int id, E changeType, boolean statusOrElse) {
		String statusOrElseString = !statusOrElse
				? changeType.getClass().getInterfaces()[0].getSimpleName()
				: changeType.getClass().getSimpleName().substring(0, changeType.getClass().getSimpleName().length() - 4);

		return format(CHANGE_TASK_SUCCESSFUL, statusOrElseString, Story.class.getSimpleName(), id, changeType);
	}
}
