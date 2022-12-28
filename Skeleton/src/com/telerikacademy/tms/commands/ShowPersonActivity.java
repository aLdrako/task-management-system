package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;

import static com.telerikacademy.tms.utils.ListingHelpers.ACTIVITY_HISTORY_HEADER;
import static com.telerikacademy.tms.utils.ListingHelpers.activityListing;
import static java.lang.String.format;

public class ShowPersonActivity implements Command {
	private static final int EXPECTED_NUMBER_PARAMETERS = 1;
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
		User user = repository.findElementByName(repository.getUsers(), name);
		return format(ACTIVITY_HISTORY_HEADER, user.getName(),
				user.getClass().getInterfaces()[0].getSimpleName()) +
				System.lineSeparator() +
				activityListing(user.getHistories());
	}
}
