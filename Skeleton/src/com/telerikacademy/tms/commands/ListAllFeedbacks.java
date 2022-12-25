package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Bug;
import com.telerikacademy.tms.models.tasks.contracts.Feedback;
import com.telerikacademy.tms.models.tasks.enums.BugStatus;
import com.telerikacademy.tms.models.tasks.enums.FeedbackStatus;
import com.telerikacademy.tms.models.tasks.enums.Rating;
import com.telerikacademy.tms.utils.ListingHelpers;
import com.telerikacademy.tms.utils.ParsingHelpers;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.beans.FeatureDescriptor;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListAllFeedbacks implements Command {
	public static final String INVALID_FILTER_OPTION_MESSAGE = "Invalid filter option. You can filter the feedbacks only by status.";
	public static final String INVALID_SORT_OPTION_MESSAGE = "Invalid sort option. You can sort the feedbacks only by title/rating.";

	private final TaskManagementRepository repository;

	public ListAllFeedbacks(TaskManagementRepository repository) {
		this.repository = repository;
	}
	@Override
	public String execute(List<String> parameters) {
		repository.createFeedback("Good Feedback sort", "Some good feedback here", Rating.SEVEN);
		repository.createFeedback("Bad Feedback sort", "Some good feedback here", Rating.EIGHT);
		ValidationHelpers.validateFilteringAndSortingParameters(parameters);
		List<Feedback> feedbacks = listWithFeedbacks();
		feedbacks = filterFeedbacks(parameters, feedbacks);
		sortFeedbacks(parameters, feedbacks);
		return ListingHelpers.elementsToString(feedbacks);
	}
	private List<Feedback> filterFeedbacks(List<String> parameters, List<Feedback> feedbacks) {
		if (parameters.get(0).equalsIgnoreCase("filterByStatus")) {
			FeedbackStatus status = ParsingHelpers.tryParseEnum(parameters.get(1), FeedbackStatus.class);
			return feedbacks.stream().filter(feedback -> feedback.getStatus() == status).collect(Collectors.toList());
		} else if (parameters.stream().anyMatch(value -> value.toLowerCase().contains("filterby"))) {
			throw new InvalidUserInputException(INVALID_FILTER_OPTION_MESSAGE);
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
	private List<Feedback> listWithFeedbacks() {
		return repository.getTasks().stream()
				.filter(task -> task instanceof Feedback)
				.map(Feedback.class::cast)
				.collect(Collectors.toList());
	}
}
