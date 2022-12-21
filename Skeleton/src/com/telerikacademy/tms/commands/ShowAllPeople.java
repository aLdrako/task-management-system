package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;

public class ShowAllPeople implements Command {
	public static final int EXPECTED_NUMBER_PARAMETERS = 0;
	private final TaskManagementRepository repository;

	public ShowAllPeople(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);

		return showAllUsers();
	}

	private String showAllUsers() {
		StringBuilder builder = new StringBuilder();
		for (User user : repository.getUsers()) {
			builder.append(user).append(System.lineSeparator());
		}
		return builder.toString();
	}


}
