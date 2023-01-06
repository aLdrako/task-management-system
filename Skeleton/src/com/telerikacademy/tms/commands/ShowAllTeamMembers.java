package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Nameable;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class ShowAllTeamMembers implements Command {
	public static final int EXPECTED_NUMBER_PARAMETERS = 1;
	public static final String MEMBERS_LISTED = "%s has (%s) team members -> ";
	public static final String NO_MEMBERS_LISTED = "%s team has no members.";
	private final TaskManagementRepository repository;

	public ShowAllTeamMembers(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		String teamName = parameters.get(0);
		Team findTeam = repository.findElementByName(repository.getTeams(), teamName);
		List<User> members = findTeam.getUsers();

		return members.size() == 0 ? format(NO_MEMBERS_LISTED, findTeam.getName()) :
				format(MEMBERS_LISTED, findTeam.getName(), members.size()) +
				members.stream().map(Nameable::getName).collect(Collectors.joining(", "));
	}
}
