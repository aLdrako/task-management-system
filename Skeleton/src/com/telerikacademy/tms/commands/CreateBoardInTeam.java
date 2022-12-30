package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.DuplicateElementException;
import com.telerikacademy.tms.models.BoardImpl;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;

public class CreateBoardInTeam implements Command {
	public static final int EXPECTED_NUMBER_PARAMETERS = 2;
	public static final String BOARD_CREATED_SUCCESSFULLY = "Board <%s> has been created in team <%s>!";
	public static final String DUPLICATE_NAME_MESSAGE = "Duplicate name. There is a User/Team with the same name!";
	private final TaskManagementRepository repository;

	public CreateBoardInTeam(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		String team = parameters.get(1);
		String name = boardExistInTeam(parameters.get(0), team);
		return createBoardInTeam(name, team);
	}

	private String boardExistInTeam(String name, String team) {
		Team t = repository.findElementByName(repository.getTeams(), team);
		if (t.getBoards().stream().anyMatch(board -> board.getName().equalsIgnoreCase(name))) {
			throw new DuplicateElementException(DUPLICATE_NAME_MESSAGE);
		}
		return name;
	}

	private String createBoardInTeam(String boardName, String teamName) {
		Team team = repository.findElementByName(repository.getTeams(), teamName);
		team.addBoard(new BoardImpl(boardName));
		return String.format(BOARD_CREATED_SUCCESSFULLY, boardName, team.getName());
	}
}
