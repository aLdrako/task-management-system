package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.utils.ListingHelpers;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;

public class ShowAllPeople implements Command {
	private static final int EXPECTED_NUMBER_PARAMETERS = 0;
	private static final String NO_PEOPLE_MESSAGE = "=== NO ADDED PEOPLE ===";
	private static final String ALL_PEOPLE_MESSAGE = "=== ALL PEOPLE ===";
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
		if (repository.getUsers().size() == 0) {
			builder.append(NO_PEOPLE_MESSAGE);
		} else {
			builder.append(ALL_PEOPLE_MESSAGE);
		}
		builder.append(System.lineSeparator());
		builder.append(ListingHelpers.elementsToString(repository.getUsers()));
		builder.append(System.lineSeparator());
		return builder.toString();
	}


}
