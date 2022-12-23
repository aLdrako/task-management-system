package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.tasks.contracts.Bug;
import com.telerikacademy.tms.models.tasks.contracts.Feedback;
import com.telerikacademy.tms.models.tasks.contracts.Story;
import com.telerikacademy.tms.models.tasks.enums.*;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;

public class CreateTaskInBoard implements Command {
	public static final int EXPECTED_MIN_NUMBER_PARAMETERS = 5;
	public static final int EXPECTED_MAX_NUMBER_PARAMETERS = 6;
	public static final String TASK_ALREADY_EXISTS = "Task already in board!";
	public static final String TASK_CREATED_SUCCESSFULLY = "Task %s has been created in board %s!";
	private final TaskManagementRepository repository;

	public CreateTaskInBoard(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		ValidationHelpers.validateArgumentCountRange(parameters, EXPECTED_MIN_NUMBER_PARAMETERS, EXPECTED_MAX_NUMBER_PARAMETERS);
		TaskType ts = TaskType.valueOf(parameters.get(0));
		String boardName = parameters.get(1);
		Board board = repository.findElementByName(repository.getBoards(), boardName);
		String title = parameters.get(2);
		String description = parameters.get(3);
		switch (ts){
			case BUG:

				PriorityType priority = PriorityType.valueOf(parameters.get(4));
				SeverityType severity = SeverityType.valueOf(parameters.get(5));
				Bug bug = repository.createBug(title, description, priority, severity);
				board.addTask(bug);
				break;
			case STORY:
				PriorityType sp = PriorityType.valueOf(parameters.get(4));
				SizeType ss = SizeType.valueOf(parameters.get(5));
				Story story = repository.createStory(title, description, sp, ss);
				board.addTask(story);
				break;
			case FEEDBACK:
				Rating fr = Rating.valueOf(parameters.get(4));
				Feedback feedback = repository.createFeedback(title,description,fr);
				board.addTask(feedback);
				break;
		}
		return String.format(TASK_CREATED_SUCCESSFULLY, title, boardName);
	}
}
