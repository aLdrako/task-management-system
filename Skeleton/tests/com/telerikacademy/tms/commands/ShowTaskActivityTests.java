package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.tasks.contracts.Feedback;
import com.telerikacademy.tms.models.tasks.enums.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.telerikacademy.tms.utils.ModelsConstants.DESCRIPTION_VALID_NAME;
import static com.telerikacademy.tms.utils.ModelsConstants.TASK_VALID_NAME;
import static com.telerikacademy.tms.utils.TestUtils.getList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShowTaskActivityTests {
	private static final int EXPECTED_NUMBER_PARAMETERS = 1;
	private Command showTaskActivityCommand;
	private TaskManagementRepository repository;

	@BeforeEach
	public void before() {
		repository = new TaskManagementRepositoryImpl();
		showTaskActivityCommand = new ShowTaskActivity(repository);
	}

	@ParameterizedTest(name = "passed arguments: {0}")
	@ValueSource(ints = {EXPECTED_NUMBER_PARAMETERS - 1, EXPECTED_NUMBER_PARAMETERS + 1})
	public void execute_Should_ThrowException_When_ArgumentsCountDiffer(int argumentsCount) {
		// Arrange
		List<String> parameters = getList(argumentsCount);

		// Act, Assert
		assertThrows(IllegalArgumentException.class, () -> showTaskActivityCommand.execute(parameters));
	}

	@Test
	public void execute_Should_ShowTaskActivity_When_ValidArgumentsPassed() {
		// Arrange
		Feedback feedback = repository.createFeedback(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, Rating.TEN);

		// Act, Assert
		assertDoesNotThrow(() -> showTaskActivityCommand.execute(List.of(String.valueOf(feedback.getID()))));
	}
}
