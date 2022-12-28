package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.UserImpl;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;

public class AddPersonToTeam implements Command {
	private static final int EXPECTED_NUMBER_PARAMETERS = 2;
	private static final String PERSON_ADDED_TO_TEAM = "Person <%s> has been added to the team <%s>!";

	private final TaskManagementRepository repository;

	public AddPersonToTeam(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		String userName = parameters.get(0);
		String teamName = parameters.get(1);
		return addMemberToTeam(userName, teamName);
	}

	private String addMemberToTeam(String userName, String teamName) {
		User user = repository.findElementByName(repository.getUsers(), userName);
		Team team = repository.findElementByName(repository.getTeams(), teamName);

		team.addUser(user);
		return String.format(PERSON_ADDED_TO_TEAM, userName, team.getName());
	}
}
