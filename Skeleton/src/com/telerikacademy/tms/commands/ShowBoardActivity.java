package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;

import static com.telerikacademy.tms.utils.ListingHelpers.elementsToString;
import static java.lang.String.format;

public class ShowBoardActivity implements Command {
	public static final int EXPECTED_NUMBER_PARAMETERS = 2;
	public static final String ACTIVITY_HISTORY_HEADER = "<<< %s's Activity History >>>" + System.lineSeparator();
	private final TaskManagementRepository repository;

	public ShowBoardActivity(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		String nameBoard = parameters.get(0);
		String nameTeam = parameters.get(1);
		Team team = repository.findElementByName(repository.getTeams(), nameTeam);
		Board board = repository.findBoardByNameInTeam(team, nameBoard);

		return format(ACTIVITY_HISTORY_HEADER, board.getName()) +
				elementsToString(board.getHistories());
	}
}
