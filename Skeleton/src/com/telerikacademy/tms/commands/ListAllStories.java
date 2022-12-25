package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Bug;
import com.telerikacademy.tms.models.tasks.contracts.Feedback;
import com.telerikacademy.tms.models.tasks.contracts.Story;
import com.telerikacademy.tms.models.tasks.enums.BugStatus;
import com.telerikacademy.tms.models.tasks.enums.StoryStatus;
import com.telerikacademy.tms.utils.ListingHelpers;
import com.telerikacademy.tms.utils.ParsingHelpers;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListAllStories implements Command {
	public static final String INVALID_FILTER_OPTION_MESSAGE = "Invalid filter option. You can filter the stories only by status/assignee.";
	public static final String INVALID_SORT_OPTION_MESSAGE = "Invalid sort option. You can sort the stories only by title/priority/size.";

	private final TaskManagementRepository repository;

	public ListAllStories(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		ValidationHelpers.validateFilteringAndSortingParameters(parameters);
		List<Story> stories = listWithStories();
		stories = filterStories(parameters, stories);
		sortStories(parameters, stories);
		return ListingHelpers.elementsToString(stories);
	}

	private void sortStories(List<String> parameters, List<Story> stories) {
		if (parameters.stream().anyMatch(value -> value.equalsIgnoreCase("sortByTitle"))) {
			Collections.sort(stories);
		} else if (parameters.stream().anyMatch(value -> value.equalsIgnoreCase("sortByPriority"))) {
			stories.sort(Comparator.comparing(Story::getPriority));
		} else if (parameters.stream().anyMatch(value -> value.equalsIgnoreCase("sortBySize"))) {
			stories.sort(Comparator.comparing(Story::getSize));
		} else if (parameters.stream().anyMatch(value -> value.toLowerCase().contains("sortby"))) {
			throw new InvalidUserInputException(INVALID_SORT_OPTION_MESSAGE);
		}
	}

	private List<Story> filterStories(List<String> parameters, List<Story> stories) {
		if (parameters.get(0).equalsIgnoreCase("filterByStatus")) {
			StoryStatus status = ParsingHelpers.tryParseEnum(parameters.get(1), StoryStatus.class);
			return stories.stream().filter(story -> story.getStatus() == status).collect(Collectors.toList());
		} else if (parameters.get(0).equalsIgnoreCase("filterByAssignee")) {
			User user = repository.findElementByName(repository.getUsers(), parameters.get(1));
			return stories.stream().filter(story -> story.getAssignee() == user)
					.collect(Collectors.toList());
		} else if (parameters.get(0).equalsIgnoreCase("filterByStatusAndAssignee")) {
			StoryStatus status = ParsingHelpers.tryParseEnum(parameters.get(1), StoryStatus.class);
			User user = repository.findElementByName(repository.getUsers(), parameters.get(2));
			return stories.stream().filter(story -> story.getStatus() == status)
					.filter(story -> story.getAssignee() == user)
					.collect(Collectors.toList());
		} else if (parameters.stream().anyMatch(value -> value.toLowerCase().contains("filterby"))) {
			throw new InvalidUserInputException(INVALID_FILTER_OPTION_MESSAGE);
		}
		return stories;
	}

	private List<Story> listWithStories() {
		return repository.getTasks().stream()
				.filter(task -> task instanceof Story)
				.map(Story.class::cast)
				.collect(Collectors.toList());
	}
}
