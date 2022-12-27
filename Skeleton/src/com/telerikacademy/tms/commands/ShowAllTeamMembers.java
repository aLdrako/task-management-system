package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;

import static java.lang.String.format;

public class ShowAllTeamMembers implements Command {
	public static final int EXPECTED_NUMBER_PARAMETERS = 1;
	public static final String MEMBERS_LISTED = "%s' has (%s) team members -> ";
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

		StringBuilder builder = new StringBuilder();
		if (members.size() == 0) {
			builder.append(format(NO_MEMBERS_LISTED, teamName));
		} else {
			builder.append(format(MEMBERS_LISTED, teamName, members.size()));
			for (User member : members) {
				builder.append(member.getName()).append(", ");
			}
			builder.deleteCharAt(builder.length() - 2);
		}
		return builder.toString();
	}
}
