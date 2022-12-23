package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;

public class ShowAllTeams implements Command {
	private static final int EXPECTED_NUMBER_PARAMETERS = 0;
	private static final String NO_TEAMS_MESSAGE = "No added teams!";
	private final TaskManagementRepository repository;

	public ShowAllTeams(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);

		return showAllTeams();
	}

	private String showAllTeams() {
		StringBuilder builder = new StringBuilder();
		if (repository.getTeams().size() == 0) builder.append(NO_TEAMS_MESSAGE);
		for (Team team : repository.getTeams()) {
			builder.append(team).append(System.lineSeparator());
		}
		return builder.toString();
	}
}
