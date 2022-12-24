package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.tasks.contracts.Bug;
import com.telerikacademy.tms.models.tasks.contracts.Task;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.Rating;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;
import com.telerikacademy.tms.models.tasks.enums.SizeType;
import com.telerikacademy.tms.utils.ListingHelpers;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ListAllTasks implements Command {
	public static final String INVALID_SORT_OPTION_MESSAGE = "Invalid sort option. You can sort the tasks only by title.";
	public static final String INVALID_FILTER_OPTION_MESSAGE = "Invalid filter option. You can filter the tasks only by title.";

	private static final String TITLE_DOES_NOT_EXIST = "There is not task that contains the given title.";

	private final TaskManagementRepository repository;

	public ListAllTasks(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		repository.createFeedback("A Good Feedback sort", "Some good feedback here", Rating.NINE);
		repository.createFeedback("Good Feedback sort", "Some good feedback here", Rating.NINE);
		repository.createBug("Very bad bug", "Some bad bug here", PriorityType.MEDIUM, SeverityType.CRITICAL);
		repository.createStory("A story begins", "Once upon a time, there was something else", PriorityType.MEDIUM, SizeType.LARGE);
		ValidationHelpers.validateFilteringAndSortingParameters(parameters);
		List<Task> tasks = repository.getTasks();
		tasks = filterTasks(parameters, tasks);
		sortTasks(parameters, tasks);

		return ListingHelpers.elementsToString(tasks);
	}

	private void sortTasks(List<String> parameters, List<Task> tasks) {
		if (parameters.stream().anyMatch(value -> value.equalsIgnoreCase("sortByTitle"))) {
			Collections.sort(tasks);
			return;
		}
		if (parameters.stream().anyMatch(value -> value.toLowerCase().contains("sortby"))) {
			throw new InvalidUserInputException(INVALID_SORT_OPTION_MESSAGE);
		}
	}

	private List<Task> filterTasks(List<String> parameters, List<Task> tasks) {
		if (parameters.get(0).equalsIgnoreCase("filterByTitle")) {
			if (tasks.stream().noneMatch(task -> task.getTitle().contains(parameters.get(1)) ||
					task.getTitle().equalsIgnoreCase(parameters.get(1)))) {
				throw new InvalidUserInputException(TITLE_DOES_NOT_EXIST);
			}
			return tasks
					.stream()
					.filter(task -> task.getTitle().contains(parameters.get(1)))
					.collect(Collectors.toList());
		}
		if (parameters.stream().anyMatch(value -> value.contains("filter"))) {
			throw new InvalidUserInputException(INVALID_FILTER_OPTION_MESSAGE);
		}
		return tasks;
	}
}