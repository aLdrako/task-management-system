package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.tasks.contracts.Feedback;
import com.telerikacademy.tms.models.tasks.contracts.Task;
import com.telerikacademy.tms.models.tasks.enums.Rating;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.telerikacademy.tms.utils.ModelsConstants.*;
import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.*;

public class ListAllFeedbacksTests {
	private TaskManagementRepository repository;
	private Command listAllFeedbacks;

	@BeforeEach
	public void before() {
		repository = new TaskManagementRepositoryImpl();
		listAllFeedbacks = new ListAllFeedbacks(repository);
	}

	@ParameterizedTest(name = "passed arguments: {0}")
	@ValueSource(strings = {"sortByTitle", "filterByStatus"})
	public void execute_Should_ThrowException_When_ReceivingParametersAfterSorting(String argument) {
		// Arrange
		Feedback feedback = repository.createFeedback(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, Rating.TEN);
		List<String> params1 = List.of("sortByTitle", argument);
		List<String> params2 = List.of("filterByStatus", valueOf(feedback.getStatus()), "sortByTitle", argument);

		// Act, Assert
		assertAll(
				() -> assertThrows(InvalidUserInputException.class, () -> listAllFeedbacks.execute(params1)),
				() -> assertThrows(IllegalArgumentException.class, () -> listAllFeedbacks.execute(params2))
		);
	}

	@Test
	public void execute_Should_ThrowException_When_ReceivingInvalidArguments() {
		// Arrange
		Task task = repository.createFeedback(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, Rating.FIVE);
		List<String> parameters = List.of(RANDOM_WORD);
		List<String> parameters1 = List.of(RANDOM_WORD, "filterByStatus", task.getStatus().toString(), "sortByTitle");
		List<String> parameters2 = List.of(RANDOM_WORD, "filterByStatus", task.getStatus().toString());
		List<String> parameters3 = List.of(RANDOM_WORD, "sortByTitle");

		// Act, Assert
		Assertions.assertAll(
				() -> Assertions.assertThrows(InvalidUserInputException.class, () -> listAllFeedbacks.execute(parameters)),
				() -> Assertions.assertThrows(InvalidUserInputException.class, () -> listAllFeedbacks.execute(parameters1)),
				() -> Assertions.assertThrows(InvalidUserInputException.class, () -> listAllFeedbacks.execute(parameters2)),
				() -> Assertions.assertThrows(InvalidUserInputException.class, () -> listAllFeedbacks.execute(parameters3))
		);
	}

	@Test
	public void execute_Should_ThrowException_When_FilterArgumentsCountDiffer() {
		// Arrange
		List<String> params = List.of("filterByStatus");

		// Act, Assert
		assertThrows(InvalidUserInputException.class, () -> listAllFeedbacks.execute(params));
	}

	@Test
	public void execute_Should_ThrowException_When_WrongParameterSpecified() {
		// Arrange
		List<String> params1 = List.of("sortBySomething");
		List<String> params2 = List.of("filterBySomething");

		// Act, Assert
		assertAll(
				() -> assertThrows(InvalidUserInputException.class, () -> listAllFeedbacks.execute(params1)),
				() -> assertThrows(InvalidUserInputException.class, () -> listAllFeedbacks.execute(params2))
		);
	}

	@Test
	public void execute_Should_ListAllFeedbacks_When_ValidFilterParametersPassed() {
		// Arrange
		Feedback feedback = repository.createFeedback(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, Rating.TEN);
		List<String> params = List.of("filterByStatus", valueOf(feedback.getStatus()));

		// Act, Assert
		assertDoesNotThrow(() -> listAllFeedbacks.execute(params));
	}

	@ParameterizedTest(name = "passed arguments: {0}")
	@ValueSource(strings = {"sortByTitle", "sortByRating"})
	public void execute_Should_ListAllFeedbacks_When_ValidSortParametersPassed(String argument) {
		// Arrange
		List<String> params = List.of(argument);

		// Act, Assert
		assertDoesNotThrow(() -> listAllFeedbacks.execute(params));
	}

	@ParameterizedTest(name = "passed arguments: {0}")
	@ValueSource(strings = {"sortBySeverity", "sortByPriority"})
	public void execute_Should_ThrowException_When_InvalidSortParametersPassed(String argument) {
		// Arrange
		List<String> params = List.of(argument);

		// Act, Assert
		assertThrows(InvalidUserInputException.class, () -> listAllFeedbacks.execute(params));
	}

	@Test
	public void execute_Should_ListAllFeedbacks_When_LastParameterIsSorting() {
		// Arrange
		Feedback feedback = repository.createFeedback(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, Rating.TEN);
		List<String> params1 = List.of("sortByTitle");
		List<String> params2 = List.of("filterByStatus", valueOf(feedback.getStatus()), "sortByTitle");

		// Act, Assert
		assertAll(
				() -> assertDoesNotThrow(() -> listAllFeedbacks.execute(params1)),
				() -> assertDoesNotThrow(() -> listAllFeedbacks.execute(params2))
		);
	}

	@Test
	public void execute_Should_ListAllFeedbacks_When_ZeroParametersSpecified() {
		// Arrange
		List<String> params = List.of();

		// Act, Assert
		assertDoesNotThrow(() -> listAllFeedbacks.execute(params));
	}
}
