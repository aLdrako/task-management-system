package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.DuplicateElementException;
import com.telerikacademy.tms.models.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.telerikacademy.tms.commands.CreateTeam.EXPECTED_NUMBER_PARAMETERS;
import static com.telerikacademy.tms.utils.ModelsConstants.TEAM_VALID_NAME;
import static com.telerikacademy.tms.utils.TestUtils.getList;

public class CreateTeamTests {

	private TaskManagementRepository repository;
	private Command command;

	@BeforeEach
	public void before() {
		repository = new TaskManagementRepositoryImpl();
		command = new CreateTeam(repository);
	}

	@ParameterizedTest(name = "with arguments count: {0}")
	@ValueSource(ints = {EXPECTED_NUMBER_PARAMETERS - 1, EXPECTED_NUMBER_PARAMETERS + 1})
	public void execute_Should_ThrowException_When_ReceivingInvalidArguments(int argumentsCount) {
		// Arrange
		List<String> parameters = getList(argumentsCount);

		// Act, Assert
		Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
	}

	@Test
	public void execute_Should_ThrowException_When_ReceivingDuplicateTeamName() {
		// Arrange
		Team team = repository.createTeam(TEAM_VALID_NAME);
		List<String> parameters = List.of(team.getName());

		//Act, Assert
		Assertions.assertThrows(DuplicateElementException.class, () -> command.execute(parameters));
	}

	@Test
	public void execute_Should_IncreaseSizeOfTeamList_When_ReceivingValidArgument() {
		// Arrange
		List<String> parameters = List.of(TEAM_VALID_NAME);
		// Act
		command.execute(parameters);
		// Assert
		Assertions.assertEquals(1, repository.getTeams().size());
	}
	@Test
	public void execute_Should_NotThrowException_When_ReceivingValidArgument() {
		// Arrange
		List<String> parameters = List.of(TEAM_VALID_NAME);
		// Act, Assert
		Assertions.assertDoesNotThrow(() -> command.execute(parameters));
	}
}
