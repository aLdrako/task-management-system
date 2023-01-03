package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.tasks.contracts.Feedback;
import com.telerikacademy.tms.models.tasks.enums.FeedbackStatus;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.telerikacademy.tms.utils.FilterHelpers.filterByStatus;
import static com.telerikacademy.tms.utils.ListingHelpers.elementsToString;
import static com.telerikacademy.tms.utils.ListingHelpers.listingCommandsSubHeader;
import static com.telerikacademy.tms.utils.ValidationHelpers.*;
import static java.lang.String.format;

public class ListAllFeedbacks implements Command {
	public static final String INVALID_COUNT_PARAMETER = "Invalid parameter count.";
	public static final String INVALID_FILTER_OPTION_MESSAGE = "Invalid filter option. You can filter the feedbacks only by status.";
	public static final String INVALID_SORT_OPTION_MESSAGE = "Invalid sort option. You can sort the feedbacks only by title/rating.";
	public static final String LISTING_HEADER = "LIST ALL FEEDBACKS %s %n%s";
	private final TaskManagementRepository repository;

	public ListAllFeedbacks(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		List<Feedback> feedbacks = repository.getFeedbacks();
		if (parameters.size() == ZERO_PARAMETERS) {
			return format(LISTING_HEADER, listingCommandsSubHeader(parameters), elementsToString(feedbacks));

		}
		validateFilteringAndSortingParameters(parameters);
		feedbacks = filterFeedbacks(parameters, feedbacks);
		validateArgumentsSorting(parameters);
		sortFeedbacks(parameters, feedbacks);
		return format(LISTING_HEADER, listingCommandsSubHeader(parameters), elementsToString(feedbacks));
	}

	private List<Feedback> filterFeedbacks(List<String> parameters, List<Feedback> feedbacks) {
		try {
			if (parameters.get(0).equalsIgnoreCase("filterByStatus")) {
				return filterByStatus(parameters.get(1), feedbacks, FeedbackStatus.class);
			} else if (parameters.get(0).toLowerCase().contains("filterby")) {
				throw new InvalidUserInputException(INVALID_FILTER_OPTION_MESSAGE);
			}
		} catch (IndexOutOfBoundsException ex) {
			throw new InvalidUserInputException(INVALID_COUNT_PARAMETER);
		}
		return feedbacks;
	}

	private void sortFeedbacks(List<String> parameters, List<Feedback> feedbacks) {
		if (parameters.stream().anyMatch(value -> value.equalsIgnoreCase("sortByTitle"))) {
			Collections.sort(feedbacks);
		} else if (parameters.stream().anyMatch(value -> value.equalsIgnoreCase("sortByRating"))) {
			feedbacks.sort(Comparator.comparing(Feedback::getRating));
		} else if (parameters.stream().anyMatch(value -> value.toLowerCase().contains("sortby"))) {
			throw new InvalidUserInputException(INVALID_SORT_OPTION_MESSAGE);
		}
	}
}
