package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.compositions.contracts.History;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;

public class ShowBoardActivity implements Command {
	public static final int EXPECTED_NUMBER_PARAMETERS = 1;

	private final TaskManagementRepository repository;

	public ShowBoardActivity(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		String name = parameters.get(0);
		return showBoardActivity(name);
	}

	private String showBoardActivity(String name) {
		Board board = repository.findElementByName(repository.getBoards(), name);
		StringBuilder builder = new StringBuilder();
		builder.append(name).append("'s activity:").append(System.lineSeparator());
		for (History activityHistory : board.getHistories()) {
			builder.append(activityHistory).append(System.lineSeparator());
		}
		return builder.toString();
	}
}
