package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.tasks.contracts.Feedback;
import com.telerikacademy.tms.models.tasks.contracts.Status;
import com.telerikacademy.tms.models.tasks.enums.FeedbackStatus;
import com.telerikacademy.tms.models.tasks.enums.Rating;

import java.util.List;

import static com.telerikacademy.tms.utils.ParsingHelpers.*;
import static com.telerikacademy.tms.utils.ValidationHelpers.validateArgumentsCount;
import static java.lang.String.format;

public class ChangeFeedback implements Command {
	private static final int EXPECTED_NUMBER_PARAMETERS = 3;
	private static final String INVALID_CHANGE_COMMAND = "Invalid command for change provided. Use: 'status' or 'rating'.";
	private static final String CHANGE_TASK_SUCCESSFUL = "%s for %s with ID -> [%d] was changed to {%s}.";
	private static final String FEEDBACK = Feedback.class.getSimpleName();
	private static final String STATUS = Status.class.getSimpleName();
	private static final String RATING = Rating.class.getSimpleName();
	private final TaskManagementRepository repository;

	public ChangeFeedback(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		int id = tryParseInt(parameters.get(0));
		String typeOfChange = parameters.get(1).toLowerCase();
		return changeFeedback(parameters, id, typeOfChange);
	}

	private String changeFeedback(List<String> parameters, int id, String typeOfChange) {
		Feedback feedback = repository.findTaskById(repository.getFeedbacks(), id, "Feedback");

		try {
			switch (typeOfChange) {
				case "status":
					FeedbackStatus feedbackStatus = tryParseEnum(parameters.get(2), FeedbackStatus.class);
					feedback.setStatus(feedbackStatus);
					return format(CHANGE_TASK_SUCCESSFUL, STATUS, FEEDBACK, id, feedbackStatus);
				case "rating":
					Rating rating = tryParseEnum(convertDigitToWord(parameters.get(2)), Rating.class);
					feedback.setRating(rating);
					return format(CHANGE_TASK_SUCCESSFUL, RATING, FEEDBACK, id, rating);
				default:
					throw new UnsupportedOperationException(INVALID_CHANGE_COMMAND);
			}
		} catch (InvalidUserInputException e) {
			throw new InvalidUserInputException(SAME_PARAMETERS_PASSED);
		}
	}
}
