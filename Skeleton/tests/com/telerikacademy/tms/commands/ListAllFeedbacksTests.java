package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.tasks.contracts.Feedback;
import com.telerikacademy.tms.models.tasks.enums.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.telerikacademy.tms.utils.ModelsConstants.DESCRIPTION_VALID_NAME;
import static com.telerikacademy.tms.utils.ModelsConstants.TASK_VALID_NAME;
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
	public void execute_Should_ThrowException_When_FilterArgumentsCountDiffer() {
		// Arrange
		List<String> params = List.of("filterByStatus");

		// Act, Assert
		assertThrows(InvalidUserInputException.class, () -> listAllFeedbacks.execute(params));
	}

	@Test
	public void execute_Should_ThrowException_When_WrongFilterParameterSpecified() {
		// Arrange
		List<String> params = List.of("filterBySomething");

		// Act, Assert
		assertThrows(InvalidUserInputException.class, () -> listAllFeedbacks.execute(params));
	}

	@Test
	public void execute_Should_ThrowException_When_WrongSortParameterSpecified() {
		// Arrange
		List<String> params = List.of("sortBySomething");

		// Act, Assert
		assertThrows(InvalidUserInputException.class, () -> listAllFeedbacks.execute(params));
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
