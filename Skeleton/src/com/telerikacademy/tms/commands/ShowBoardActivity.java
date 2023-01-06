package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;

import static com.telerikacademy.tms.utils.ListingHelpers.activityListing;
import static java.lang.String.format;

public class ShowBoardActivity implements Command {
	public static final int EXPECTED_NUMBER_PARAMETERS = 2;
	public static final String ACTIVITY_HISTORY_BOARD_HEADER = "<<< <%s> board's %s ACTIVITY HISTORY >>>";

	public final TaskManagementRepository repository;

	public ShowBoardActivity(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		String boardName = parameters.get(0);
		String teamName = parameters.get(1);

		return showBoardActivity(boardName, teamName);
	}

	private String showBoardActivity(String boardName, String teamName) {
		Team team = repository.findElementByName(repository.getTeams(), teamName);
		Board board = repository.findBoardByNameInTeam(team, boardName);
		return format(ACTIVITY_HISTORY_BOARD_HEADER, team.getName(),
				board.getName()) +
				System.lineSeparator() +
				activityListing(board.getHistories());
	}
}
