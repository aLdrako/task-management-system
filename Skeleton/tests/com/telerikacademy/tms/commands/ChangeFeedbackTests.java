package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.tasks.contracts.Feedback;
import com.telerikacademy.tms.models.tasks.enums.FeedbackStatus;
import com.telerikacademy.tms.models.tasks.enums.Rating;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.telerikacademy.tms.utils.ModelsConstants.DESCRIPTION_VALID_NAME;
import static com.telerikacademy.tms.utils.ModelsConstants.TASK_VALID_NAME;
import static com.telerikacademy.tms.utils.TestUtils.getList;
import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.*;

public class ChangeFeedbackTests {
	private static final int EXPECTED_NUMBER_PARAMETERS = 3;
	private Command changeFeedback;
	private TaskManagementRepository repository;

	@BeforeEach
	public void before() {
		repository = new TaskManagementRepositoryImpl();
		changeFeedback = new ChangeFeedback(repository);
	}

	@ParameterizedTest(name = "passed arguments: {0}")
	@ValueSource(ints = {EXPECTED_NUMBER_PARAMETERS - 1, EXPECTED_NUMBER_PARAMETERS + 1})
	public void execute_Should_ThrowException_When_ArgumentsCountDiffer(int argumentsCount) {
		// Arrange
		List<String> parameters = getList(argumentsCount);

		//Act, Assert
		assertThrows(IllegalArgumentException.class, () -> changeFeedback.execute(parameters));
	}

	@Test
	public void execute_Should_ThrowException_When_WrongChangeParameterProvided() {
		// Arrange
		Feedback feedback = repository.createFeedback(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, Rating.TEN);

		// Act, Assert
		assertThrows(UnsupportedOperationException.class,
				() -> changeFeedback.execute(List.of(valueOf(feedback.getID()), "severity", valueOf(SeverityType.MAJOR))));
	}

	@Test
	public void execute_Should_ThrowException_When_EnumParameterDoesNotMatch() {
		// Arrange
		Feedback feedback = repository.createFeedback(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, Rating.TEN);

		// Act, Assert
		assertAll(
				() -> assertThrows(IllegalArgumentException.class,
						() -> changeFeedback.execute(List.of(valueOf(feedback.getID()), "status", valueOf(Rating.NINE)))),
				() -> assertThrows(IllegalArgumentException.class,
						() -> changeFeedback.execute(List.of(valueOf(feedback.getID()), "rating", valueOf(FeedbackStatus.SCHEDULED))))
		);
	}

	@Test
	public void execute_Should_ThrowException_When_ChangingToSameParameter() {
		// Arrange
		Feedback feedback = repository.createFeedback(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, Rating.TEN);

		// Act, Assert
		assertAll(
				() -> assertThrows(InvalidUserInputException.class,
						() -> changeFeedback.execute(List.of(valueOf(feedback.getID()), "status", valueOf(FeedbackStatus.NEW)))),
				() -> assertThrows(InvalidUserInputException.class,
						() -> changeFeedback.execute(List.of(valueOf(feedback.getID()), "rating", valueOf(Rating.TEN))))
		);
	}

	@Test
	public void execute_Should_ChangeBugParameters_When_ValidArgumentsPassed() {
		// Arrange
		Feedback feedback = repository.createFeedback(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, Rating.TEN);

		// Act
		changeFeedback.execute(List.of(valueOf(feedback.getID()), "status", valueOf(FeedbackStatus.UNSCHEDULED)));
		changeFeedback.execute(List.of(valueOf(feedback.getID()), "rating", valueOf(Rating.NINE)));

		// Assert
		assertAll(
				() -> assertEquals(FeedbackStatus.UNSCHEDULED, feedback.getStatus()),
				() -> assertEquals(Rating.NINE, feedback.getRating())
		);
	}
}
