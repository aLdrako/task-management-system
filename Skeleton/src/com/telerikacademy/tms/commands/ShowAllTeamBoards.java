package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;

import static java.lang.String.format;

public class ShowAllTeamBoards implements Command {
	public static final int EXPECTED_NUMBER_PARAMETERS = 1;
	public static final String BOARDS_LISTED = "%s has (%s) team boards -> ";
	public static final String NO_BOARDS_LISTED = "%s team has no boards.";
	private final TaskManagementRepository repository;

	public ShowAllTeamBoards(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		String teamName = parameters.get(0);
		Team findTeam = repository.findElementByName(repository.getTeams(), teamName);
		List<Board> boards = findTeam.getBoards();

		StringBuilder builder = new StringBuilder();
		if (boards.size() == 0) {
			builder.append(format(NO_BOARDS_LISTED, teamName));
		} else {
			builder.append(format(BOARDS_LISTED, teamName, boards.size()));
			for (Board board : boards) {
				builder.append(board.getName()).append(", ");
			}
			builder.deleteCharAt(builder.length() - 2);
		}
		return builder.toString();
	}
}
