package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.compositions.contracts.History;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;

import static com.telerikacademy.tms.utils.ListingHelpers.ACTIVITY_HISTORY_HEADER;
import static java.lang.String.format;

public class ShowBoardActivity implements Command {
	private static final int EXPECTED_NUMBER_PARAMETERS = 2;
	private final TaskManagementRepository repository;

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

		StringBuilder builder = new StringBuilder();
		builder.append(format(ACTIVITY_HISTORY_HEADER, boardName, board.getClass().getSimpleName())).append(System.lineSeparator());
		for (History activityHistory : board.getHistories()) {
			builder.append(activityHistory).append(System.lineSeparator());
		}
		return builder.toString();
	}
}
