package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;

public class ShowAllTeamBoards implements Command {
	public static final int EXPECTED_NUMBER_PARAMETERS = 1;

	public static final String BOARDS_LISTED = "Team %s boards printed";
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
		for (Board board:boards) {
			System.out.println(board.getName());
		}
		return String.format(BOARDS_LISTED, teamName);
	}
}
