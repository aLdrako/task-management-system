package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;

public class CreatePerson implements Command {
	public static final int EXPECTED_NUMBER_PARAMETERS = 1;
	public static final String DUPLICATE_NAME_MESSAGE = "Duplicate name. Please enter a unique name!";
	public static final String USER_CREATED_MESSAGE = "User with a name %s was created.";

	private final TaskManagementRepository repository;

	public CreatePerson(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		String name = parameters.get(0);

		return createPerson(repository, name);
	}

	private String createPerson(TaskManagementRepository repository, String name) {
		if (!repository.isUniqueName(name)) {
			throw new IllegalArgumentException(DUPLICATE_NAME_MESSAGE);
		}
		User user = repository.createUser(name);
		return String.format(USER_CREATED_MESSAGE, name);
	}
}