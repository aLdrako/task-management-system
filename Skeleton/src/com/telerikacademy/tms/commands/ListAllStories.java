package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.tasks.contracts.Story;
import com.telerikacademy.tms.models.tasks.enums.StoryStatus;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.telerikacademy.tms.utils.FilterHelpers.filterByAssignee;
import static com.telerikacademy.tms.utils.FilterHelpers.filterByStatus;
import static com.telerikacademy.tms.utils.ListingHelpers.elementsToString;
import static com.telerikacademy.tms.utils.ListingHelpers.listingCommandsSubHeader;
import static com.telerikacademy.tms.utils.ValidationHelpers.*;
import static java.lang.String.format;

public class ListAllStories implements Command {
	public static final String INVALID_COUNT_PARAMETER = "Invalid parameter count.";
	public static final String INVALID_FILTER_OPTION_MESSAGE = "Invalid filter option. You can filter the stories only by status/assignee.";
	public static final String INVALID_SORT_OPTION_MESSAGE = "Invalid sort option. You can sort the stories only by title/priority/size.";
	public static final String LISTING_HEADER = "LIST ALL STORIES %s %n%s";

	private final TaskManagementRepository repository;

	public ListAllStories(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		List<Story> stories = repository.getStories();
		if (parameters.size() == ZERO_PARAMETERS) {
			return format(LISTING_HEADER, listingCommandsSubHeader(parameters), elementsToString(stories));
		}
		validateFilteringAndSortingParameters(parameters);
		validateArgumentsSorting(parameters);
		stories = filterStories(parameters, stories);
		sortStories(parameters, stories);
		return format(LISTING_HEADER, listingCommandsSubHeader(parameters), elementsToString(stories));
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
		try {
			if (parameters.get(0).equalsIgnoreCase("filterByStatus")) {
				return filterByStatus(parameters.get(1), stories, StoryStatus.class);
			} else if (parameters.get(0).equalsIgnoreCase("filterByAssignee")) {
				return filterByAssignee(parameters.get(1), stories, repository);
			} else if (parameters.get(0).equalsIgnoreCase("filterByStatusAndAssignee")) {
				stories = filterByStatus(parameters.get(1), stories, StoryStatus.class);
				return filterByAssignee(parameters.get(2), stories, repository);
			} else if (parameters.get(0).toLowerCase().contains("filterby")) {
				throw new InvalidUserInputException(INVALID_FILTER_OPTION_MESSAGE);
			}
		} catch (IndexOutOfBoundsException ex) {
			throw new InvalidUserInputException(INVALID_COUNT_PARAMETER);
		}
		return stories;
	}
}
