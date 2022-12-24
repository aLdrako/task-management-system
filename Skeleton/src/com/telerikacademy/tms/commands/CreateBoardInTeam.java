package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;

public class CreateBoardInTeam implements Command {
	public static final int EXPECTED_NUMBER_PARAMETERS = 2;

	public static final String BOARD_ALREADY_EXISTS = "Board already in team!";
	public static final String BOARD_CREATED_SUCCESSFULLY = "Board %s has been created in team %s!";
	private final TaskManagementRepository repository;

	public CreateBoardInTeam(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		String name = parameters.get(0);
		String team = parameters.get(1);
		return createBoardInTeam(name, team);
	}

	private String createBoardInTeam(String boardName, String teamName) {
		Team team = repository.findElementByName(repository.getTeams(), teamName);
		if (!repository.isBoardNameUniqueInTeam(team, boardName)) {
			throw new IllegalArgumentException(BOARD_ALREADY_EXISTS);
		}
		Board board = repository.createBoard(boardName);
		team.addBoard(board);
		return String.format(BOARD_CREATED_SUCCESSFULLY, board, team);
	}
}
