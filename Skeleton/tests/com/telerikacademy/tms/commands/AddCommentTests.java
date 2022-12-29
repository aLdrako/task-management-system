package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Bug;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.telerikacademy.tms.utils.ModelsConstants.*;
import static com.telerikacademy.tms.utils.TestUtils.getList;
import static org.junit.jupiter.api.Assertions.*;

public class AddCommentTests {
	private static final int EXPECTED_NUMBER_PARAMETERS = 3;
	private Command addCommentCommand;
	private TaskManagementRepository repository;

	@BeforeEach
	public void before() {
		repository = new TaskManagementRepositoryImpl();
		addCommentCommand = new AddComment(repository);
	}

	//TODO fix it
	@ParameterizedTest(name = "passed arguments: {0}")
	@ValueSource(ints = {EXPECTED_NUMBER_PARAMETERS - 1, EXPECTED_NUMBER_PARAMETERS + 1})
	public void execute_Should_ThrowException_When_ArgumentsCountDiffer(int argumentsCount) {
		// Arrange
		List<String> parameters = getList(argumentsCount);

		//Act, Assert
		assertThrows(IllegalArgumentException.class, () -> addCommentCommand.execute(parameters));
	}

	@Test
	public void execute_Should_AddComment_When_ValidArgumentsPassed() {
		// Arrange
		Bug bug = repository.createBug(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SeverityType.MINOR, getList(0));
		User user = repository.createUser(USER_VALID_NAME);

		// Act
		addCommentCommand.execute(List.of(String.valueOf(bug.getID()), COMMENT_MESSAGE, user.getName()));

		// Assert
		assertAll(
				() -> assertEquals(1, repository.getTasks().get(0).getComments().size()),
				() -> assertEquals(2, repository.getUsers().get(0).getHistories().size())
		);
	}
}
