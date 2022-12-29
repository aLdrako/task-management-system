package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Story;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SizeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.telerikacademy.tms.utils.ModelsConstants.*;
import static com.telerikacademy.tms.utils.TestUtils.getList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssignTaskTests {
	private static final int EXPECTED_NUMBER_PARAMETERS = 2;
	private Command assignTask;
	private TaskManagementRepository repository;

	@BeforeEach
	public void before() {
		repository = new TaskManagementRepositoryImpl();
		assignTask = new AssignTask(repository);
	}

	@ParameterizedTest(name = "passed arguments: {0}")
	@ValueSource(ints = {EXPECTED_NUMBER_PARAMETERS - 1, EXPECTED_NUMBER_PARAMETERS + 1})
	public void execute_Should_ThrowException_When_ArgumentsCountDiffer(int argumentsCount) {
		// Arrange
		List<String> parameters = getList(argumentsCount);

		//Act, Assert
		assertThrows(IllegalArgumentException.class, () -> assignTask.execute(parameters));
	}

	@Test
	public void execute_Should_ThrowException_When_TaskAlreadyAssigned() {
		// Arrange
		Story story = repository.createStory(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SizeType.LARGE);
		User user = repository.createUser(USER_VALID_NAME);

		// Act
		story.setAssignee(user);

		// Assert
		assertThrows(InvalidUserInputException.class, () -> assignTask.execute(List.of(String.valueOf(story.getID()), user.getName())));
	}

	@Test
	public void execute_Should_ThrowException_When_UserNotFoundInTeam() {
		// Arrange, Act
		Story story = repository.createStory(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SizeType.LARGE);
		User user = repository.createUser(USER_VALID_NAME);
		Team team = repository.createTeam(TEAM_VALID_NAME);
		Board board = repository.createBoard(BOARD_VALID_NAME);
		board.addTask(story);
		team.addBoard(board);

		// Assert
		assertThrows(InvalidUserInputException.class, () -> assignTask.execute(List.of(String.valueOf(story.getID()), user.getName())));
	}

	@Test
	public void execute_Should_AssignTask_When_ValidArgumentsPassed() {
		// Arrange, Act
		Story story = repository.createStory(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SizeType.LARGE);
		User user = repository.createUser(USER_VALID_NAME);
		Team team = repository.createTeam(TEAM_VALID_NAME);
		Board board = repository.createBoard(BOARD_VALID_NAME);
		board.addTask(story);
		team.addBoard(board);
		team.addUser(user);

		// Assert
		assertDoesNotThrow(() -> assignTask.execute(List.of(String.valueOf(story.getID()), user.getName())));
	}
}
