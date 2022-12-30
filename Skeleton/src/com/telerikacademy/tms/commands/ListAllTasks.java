package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.tasks.contracts.Task;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.telerikacademy.tms.utils.ListingHelpers.elementsToString;
import static com.telerikacademy.tms.utils.ListingHelpers.listingCommandsSubHeader;
import static com.telerikacademy.tms.utils.ValidationHelpers.*;
import static java.lang.String.format;

public class ListAllTasks implements Command {
	public static final String INVALID_SORT_OPTION_MESSAGE = "Invalid sort option. You can sort the tasks only by title.";
	public static final String INVALID_FILTER_OPTION_MESSAGE = "Invalid filter option. You can filter the tasks only by title.";
	private static final String TITLE_DOES_NOT_EXIST = "There is not task that contains the given title.";
	public static final String LISTING_HEADER = "LIST ALL TASKS %s %n%s";

	private final TaskManagementRepository repository;

	public ListAllTasks(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		List<Task> tasks = repository.getTasks();
		if (parameters.size() == ZERO_PARAMETERS) {
			return format(LISTING_HEADER, listingCommandsSubHeader(parameters), elementsToString(tasks));
		}
		validateFilteringAndSortingParameters(parameters);

		tasks = filterTasks(parameters, tasks);
		validateArgumentsSorting(parameters);
		sortTasks(parameters, tasks);
		return format(LISTING_HEADER, listingCommandsSubHeader(parameters), elementsToString(tasks));
	}

	private void sortTasks(List<String> parameters, List<Task> tasks) {
		if (parameters.stream().anyMatch(value -> value.equalsIgnoreCase("sortByTitle"))) {
			Collections.sort(tasks);
		} else if (parameters.stream().anyMatch(value -> value.toLowerCase().contains("sortby"))) {
			throw new InvalidUserInputException(INVALID_SORT_OPTION_MESSAGE);
		}
	}

	private List<Task> filterTasks(List<String> parameters, List<Task> tasks) {
		if (parameters.get(0).equalsIgnoreCase("filterByTitle")) {
			if (tasks.stream().noneMatch(task -> task.getTitle().toLowerCase().contains(parameters.get(1).toLowerCase().strip()) ||
					task.getTitle().equalsIgnoreCase(parameters.get(1)))) {
				throw new InvalidUserInputException(TITLE_DOES_NOT_EXIST);
			}
			return tasks
					.stream()
					.filter(task -> task.getTitle().toLowerCase().contains(parameters.get(1).toLowerCase().strip()))
					.collect(Collectors.toList());
		} else if (parameters.get(0).toLowerCase().contains("filterby")) {
			throw new InvalidUserInputException(INVALID_FILTER_OPTION_MESSAGE);
		}
		return tasks;
	}
}