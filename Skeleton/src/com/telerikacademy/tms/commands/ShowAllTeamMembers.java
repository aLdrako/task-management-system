package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

public class ShowAllTeamMembers implements Command {
	public static final int EXPECTED_NUMBER_PARAMETERS = 1;
	public static final String MEMEBERS_LISTED = "Team %s members printed";
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
		for (User member:members) {
			System.out.println(member.getName());
		}
		return String.format(MEMEBERS_LISTED, teamName);
	}
}
