package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.compositions.contracts.History;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;

public class ShowTeamActivity implements Command {
	private static final int EXPECTED_NUMBER_PARAMETERS = 1;
	private final TaskManagementRepository repository;

	public ShowTeamActivity(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		String name = parameters.get(0);
		return showTeamActivity(name);
	}

	private String showTeamActivity(String name) {
		Team team = repository.findElementByName(repository.getTeams(), name);
		StringBuilder builder = new StringBuilder();
		builder.append(name).append("'s team activity:").append(System.lineSeparator());
		for (History history : team.getHistories()) {
			builder.append(history).append(System.lineSeparator());
		}
		return builder.toString();
	}
}
