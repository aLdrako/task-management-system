package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.tasks.contracts.Feedback;
import com.telerikacademy.tms.models.tasks.enums.FeedbackStatus;
import com.telerikacademy.tms.models.tasks.enums.Rating;

import java.util.List;

import static com.telerikacademy.tms.utils.ParsingHelpers.*;
import static com.telerikacademy.tms.utils.ValidationHelpers.validateArgumentsCount;
import static java.lang.String.format;

public class ChangeFeedback implements Command {
	private static final int EXPECTED_NUMBER_PARAMETERS = 3;
	private static final String INVALID_CHANGE_COMMAND = "Invalid command for change provided. Use: 'status' or 'rating'.";
	private final TaskManagementRepository repository;

	public ChangeFeedback(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		int id = tryParseInt(parameters.get(0));
		String typeOfChange = parameters.get(1);

		return changeFeedback(parameters, id, typeOfChange);
	}

	private String changeFeedback(List<String> parameters, int id, String typeOfChange) {
		Feedback feedback;
		try {
			feedback = (Feedback) repository.findElementById(repository.getTasks(), id);
		} catch (ClassCastException e) {
			throw new ClassCastException(format(INVALID_TASK_ID_IN_CATEGORY, id, Feedback.class.getSimpleName()));
		}

		try {
			switch (typeOfChange) {
				case "status":
					FeedbackStatus feedbackStatus = tryParseEnum(parameters.get(2), FeedbackStatus.class);
					if (feedback.getStatus().equals(feedbackStatus)) throw new InvalidUserInputException();
					feedback.setStatus(feedbackStatus);
					return getFormatedString(id, feedbackStatus, false);
				case "rating":
					Rating rating = tryParseEnum(convertDigitToWord(parameters.get(2)), Rating.class);
					if (feedback.getRating().equals(rating)) throw new InvalidUserInputException();
					feedback.setRating(rating);
					return getFormatedString(id, rating, true);
				default:
					throw new InvalidUserInputException(INVALID_CHANGE_COMMAND);
			}
		} catch (InvalidUserInputException e) {
			throw new InvalidUserInputException(SAME_PARAMETERS_PASSED);
		}
	}

	private static <E extends Enum<E>> String getFormatedString(int id, E changeType, boolean statusOrElse) {
		String statusOrElseString = !statusOrElse
				? changeType.getClass().getInterfaces()[0].getSimpleName()
				: changeType.getClass().getSimpleName();

		return format(CHANGE_TASK_SUCCESSFUL, statusOrElseString, Feedback.class.getSimpleName(), id, changeType);
	}
}
