package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.tasks.contracts.Bug;
import com.telerikacademy.tms.models.tasks.contracts.Feedback;
import com.telerikacademy.tms.models.tasks.contracts.Story;
import com.telerikacademy.tms.models.tasks.enums.*;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;

import static com.telerikacademy.tms.utils.ParsingHelpers.convertDigitToWord;
import static com.telerikacademy.tms.utils.ParsingHelpers.tryParseEnum;

public class CreateTaskInBoard implements Command {
	public static final int EXPECTED_MIN_NUMBER_PARAMETERS = 6;
	public static final int EXPECTED_MAX_NUMBER_PARAMETERS = 7;
	public static final String INVALID_PARAMETER_COUNT = "Invalid parameter count";
	public static final String TASK_CREATED_SUCCESSFULLY = "Task <%s> with ID -> [%d] has been created in board <%s>!";
	private final TaskManagementRepository repository;

	public CreateTaskInBoard(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		ValidationHelpers.validateArgumentCountRange(parameters, EXPECTED_MIN_NUMBER_PARAMETERS, EXPECTED_MAX_NUMBER_PARAMETERS);
		TaskType ts = tryParseEnum(parameters.get(0), TaskType.class);
		String boardName = parameters.get(1);
		String teamName = parameters.get(2);
		Team team = repository.findElementByName(repository.getTeams(), teamName);
		Board board = repository.findBoardByNameInTeam(team, boardName);
		String title = parameters.get(3);
		String description = parameters.get(4);
		int id = 0;
		try {
			switch (ts) {
				case BUG: {
					PriorityType priority = tryParseEnum(parameters.get(5), PriorityType.class);
					SeverityType severity = tryParseEnum(parameters.get(6), SeverityType.class);
					Bug bug = repository.createBug(title, description, priority, severity);
					id = bug.getID();
					board.addTask(bug);
					break;
				}
				case STORY: {
					PriorityType priority = tryParseEnum(parameters.get(5), PriorityType.class);
					SizeType size = tryParseEnum(parameters.get(6), SizeType.class);
					Story story = repository.createStory(title, description, priority, size);
					id = story.getID();
					board.addTask(story);
					break;
				}
				case FEEDBACK: {
					Rating rating = tryParseEnum(convertDigitToWord(parameters.get(5)), Rating.class);
					Feedback feedback = repository.createFeedback(title, description, rating);
					id = feedback.getID();
					board.addTask(feedback);
					break;
				}
			}
			return String.format(TASK_CREATED_SUCCESSFULLY, title, id, boardName);
		} catch (IndexOutOfBoundsException ex) {
			throw new InvalidUserInputException(INVALID_PARAMETER_COUNT);
		}
	}
}
