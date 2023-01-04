package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.ElementNotFoundException;
import com.telerikacademy.tms.models.contracts.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.telerikacademy.tms.utils.ModelsConstants.USER_VALID_NAME;
import static com.telerikacademy.tms.utils.TestUtils.getList;

public class ShowPersonActivityTests {
	private static final int EXPECTED_NUMBER_PARAMETERS = 1;
	private TaskManagementRepository repository;
	private Command command;


	@BeforeEach
	public void before() {
		repository = new TaskManagementRepositoryImpl();
		command = new ShowPersonActivity(repository);
	}

	@ParameterizedTest(name = "with arguments count: {0}")
	@ValueSource(ints = {EXPECTED_NUMBER_PARAMETERS - 1, EXPECTED_NUMBER_PARAMETERS + 1})
	public void execute_Should_ThrowException_When_ArgumentsCountIsDifferentThanExpected(int argumentsCount) {
		// Arrange
		List<String> parameters = getList(argumentsCount);

		//Act, Assert
		Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
	}

	@Test
	public void execute_Should_ThrowException_When_UserDoesNotExistInRepository() {
		// Arrange
		List<String> parameters = List.of(USER_VALID_NAME);
		// Act, Assert
		Assertions.assertThrows(ElementNotFoundException.class, () -> command.execute(parameters));
	}

	@Test
	public void execute_Should_printPersonActivity_When_InputValid() {
		// Arrange
		User user = repository.createUser(USER_VALID_NAME);
		List<String> parameters = List.of(user.getName());

		// Act, Assert
		Assertions.assertDoesNotThrow(() -> command.execute(parameters));
	}

}
