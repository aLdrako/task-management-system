package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;

public class AddPersonToTeam implements Command {

	public static final int EXPECTED_NUMBER_PARAMETERS = 2;

	public static final String DUPLICATE_NAME_MESSAGE = "Duplicate name. Member already added!";
	public static final String TEAM_NON_EXIST = "Team does not exist!";
	public static final String PERSON_NOT_FOUND = "Person not found!";
	public static final String PERSON_ADDED_TO_TEAM = "Person %s has been added to the team %s!";

	private final TaskManagementRepository repository;

	public AddPersonToTeam(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		String name = parameters.get(0);
		String team = parameters.get(1);
		return addMemberToTeam(name, team);
	}

	private String addMemberToTeam(String name, String teamName) {
		User user = repository.findElementByName(repository.getUsers(), name);
		Team team = repository.findElementByName(repository.getTeams(), teamName);
		team.addUser(user);
		return String.format(PERSON_ADDED_TO_TEAM, name, team);
	}
}
