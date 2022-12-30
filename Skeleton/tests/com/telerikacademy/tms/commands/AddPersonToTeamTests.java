package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.ElementNotFoundException;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.contracts.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.tms.commands.AddPersonToTeam.EXPECTED_NUMBER_PARAMETERS;
import static com.telerikacademy.tms.utils.TestUtils.getList;

class AddPersonToTeamTests {

	private TaskManagementRepository repository;
	private Command command;

	@BeforeEach
	void setUp() {
		repository = new TaskManagementRepositoryImpl();
		command = new AddPersonToTeam(repository);
	}

	@Test
	void execute_Should_ThrowException_When_NumberOfArgumentsIsInvalid() {
		// Arrange
		List<String> parameters = getList(EXPECTED_NUMBER_PARAMETERS + 1);

		// Act, Assert
		Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));

		Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(getList(EXPECTED_NUMBER_PARAMETERS - 1)));
	}

	@Test
	void execute_Should_ThrowException_When_NonExistentUserIsPassed() {
		//Arrange
		Team team = repository.createTeam("Team 01");
		List<String> parameters = new ArrayList<String>();
		parameters.add("Fake User");
		parameters.add("Team 01");
		//Act, Assert
		Assertions.assertThrows(ElementNotFoundException.class, () -> command.execute(parameters));
	}

	@Test
	void execute_Should_ThrowException_When_NonExistentTeamIsPassed() {
		//Arrange
		User user = repository.createUser("New User");
		List<String> parameters = new ArrayList<String>();
		parameters.add("New User");
		parameters.add("Fake Team");
		//Act, Assert
		Assertions.assertThrows(ElementNotFoundException.class, () -> command.execute(parameters));
	}

	@Test
	void execute_Should_AddUserToSpecifiedTeam() {
		//Arrange
		User user = repository.createUser("New User");
		Team team = repository.createTeam("Team 01");
		List<String> parameters = new ArrayList<String>();
		parameters.add("New User");
		parameters.add("Team 01");
		//Act
		String result = command.execute(parameters);
		//Assert
		List<User> users = team.getUsers();
		Assertions.assertEquals("New User", users.get(0).getName());
	}
}