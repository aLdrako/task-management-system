package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.compositions.contracts.History;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;

public class ShowPersonActivity implements Command {
	public static final int EXPECTED_NUMBER_PARAMETERS = 1;
	private final TaskManagementRepository repository;

	public ShowPersonActivity(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		String name = parameters.get(0);
		return showPersonActivity(name);
	}

	private String showPersonActivity(String name) {
		StringBuilder builder = new StringBuilder();
		User user = repository.findElementByName(repository.getUsers(), name);
		for (History activityHistory : user.getHistories()) {
			builder.append(activityHistory.getHistory()).append(System.lineSeparator());
		}
		return builder.toString();
	}
}
