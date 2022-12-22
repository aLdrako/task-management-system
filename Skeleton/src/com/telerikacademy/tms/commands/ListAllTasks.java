package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.tasks.contracts.Task;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ListAllTasks implements Command {
	public static final int EXPECTED_MAX_NUMBER_PARAMETERS = 3;
	public static final String INVALID_PARAMETER_MESSAGE = "Invalid parameter for listing";
	public static final String LIST_ALREADY_SORTED = "List has already been sorted.";
	public static final String TITLE_DOES_NOT_EXIST = "This title does not exist.";

	private final TaskManagementRepository repository;

	public ListAllTasks(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		ValidationHelpers.validateArgumentsCountTill(parameters, EXPECTED_MAX_NUMBER_PARAMETERS);
		List<Task> tasks;
		if (parameters.get(0).equalsIgnoreCase("filterByTitle")) {
			String title = parameters.get(1);
			tasks = new ArrayList<>(filterTasks(title));
		} else if (parameters.get(0).equalsIgnoreCase("sortByTitle")){
			Collections.sort(repository.getTasks());
			tasks = new ArrayList<>(repository.getTasks());
		} else {
			throw new IllegalArgumentException(INVALID_PARAMETER_MESSAGE);
		}
		if (parameters.size() == 3 && parameters.get(2).equalsIgnoreCase("sortByTitle")) {
			if (parameters.get(0).equalsIgnoreCase("filterByTitle")) {
				Collections.sort(tasks);
			} else {
				throw new IllegalArgumentException(LIST_ALREADY_SORTED);
			}
		}

		return listAllTasks(tasks);
	}

	private List<Task> filterTasks(String title) {
		if (!titleExists(title)){
			throw new IllegalArgumentException(TITLE_DOES_NOT_EXIST);
		}
		return repository.getTasks()
				.stream()
				.filter(task -> task.getTitle().contains(title))
				.collect(Collectors.toList());
	}

	private boolean titleExists(String title) {
		for (Task task : repository.getTasks()) {
			if (task.getTitle().equalsIgnoreCase(title)){
				return true;
			}
		}
		return false;
	}

	private String listAllTasks(List<Task> tasks) {
		StringBuilder builder = new StringBuilder();
		for (Task task : tasks) {
			builder.append(task).append(System.lineSeparator());
		}
		return builder.toString();
	}
}
