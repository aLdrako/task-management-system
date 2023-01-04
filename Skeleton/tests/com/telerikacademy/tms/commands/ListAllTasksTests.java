package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Story;
import com.telerikacademy.tms.models.tasks.contracts.Task;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.Rating;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;
import com.telerikacademy.tms.models.tasks.enums.SizeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;


import static com.telerikacademy.tms.utils.ListingHelpers.elementsToString;
import static com.telerikacademy.tms.utils.ListingHelpers.listingCommandsSubHeader;
import static com.telerikacademy.tms.utils.ModelsConstants.*;
import static com.telerikacademy.tms.utils.TestUtils.getList;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.*;

public class ListAllTasksTests {
	private static final String LISTING_HEADER = "LIST ALL TASKS %s %n%s";
    private TaskManagementRepository repository;
	private Command listAllTasks;


	@BeforeEach
	public void before() {
		repository = new TaskManagementRepositoryImpl();
		listAllTasks = new ListAllTasks(repository);
	}

	@ParameterizedTest(name = "passed arguments: {0}")
	@ValueSource(strings = {"sortByTitle", "filterByTitle"})
	public void execute_Should_ThrowException_When_ReceivingParametersAfterSorting(String argument) {
		// Arrange
		Task task = repository.createFeedback(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, Rating.FIVE);
		List<String> parameters = List.of("sortByTitle", argument);
		List<String> parameters1 = List.of("filterByTitle", task.getTitle(), "sortByTitle", argument);


		// Act, Assert
		Assertions.assertAll(
				() -> Assertions.assertThrows(InvalidUserInputException.class, () -> listAllTasks.execute(parameters)),
				() -> Assertions.assertThrows(IllegalArgumentException.class, () -> listAllTasks.execute(parameters1))
		);
	}
	@Test
	public void execute_Should_ThrowException_When_ReceivingInvalidArguments() {
		// Arrange
		Task task = repository.createFeedback(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, Rating.FIVE);
		List<String> parameters = List.of(RANDOM_WORD);
		List<String> parameters1 = List.of(RANDOM_WORD, "filterByTitle", task.getTitle(), "sortByTitle");
		List<String> parameters2 = List.of(RANDOM_WORD, "filterByTitle", task.getTitle());
		List<String> parameters3 = List.of(RANDOM_WORD, "sortByTitle");

		// Act, Assert
		Assertions.assertAll(
				() -> Assertions.assertThrows(InvalidUserInputException.class, () -> listAllTasks.execute(parameters)),
				() -> Assertions.assertThrows(InvalidUserInputException.class, () -> listAllTasks.execute(parameters1)),
				() -> Assertions.assertThrows(InvalidUserInputException.class, () -> listAllTasks.execute(parameters2)),
				() -> Assertions.assertThrows(InvalidUserInputException.class, () -> listAllTasks.execute(parameters3))
		);
	}

	@ParameterizedTest(name = "passed arguments: {0}")
	@ValueSource(strings = {"sortByTitle"})
	public void execute_Should_ListAllTasks_When_LastParameterIsSorting(String argument) {
		// Arrange
		Task task = repository.createFeedback(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, Rating.FIVE);
		List<String> parameters = List.of(argument);
		List<String> parameters1 = List.of("filterByTitle", task.getTitle(), argument);


		// Act, Assert
		Assertions.assertAll(
				() -> Assertions.assertDoesNotThrow(() -> listAllTasks.execute(parameters)),
				() -> Assertions.assertDoesNotThrow(() -> listAllTasks.execute(parameters1))
		);
	}
	@Test
	public void execute_Should_ListAllTasks_When_ValidFilterParametersPassed() {
		// Arrange
		Story story = repository.createStory(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SizeType.SMALL);
		User user = repository.createUser(USER_VALID_NAME);
		story.setAssignee(user);
		List<String> params = List.of("filterByTitle", valueOf(story.getTitle()));

		// Act, Assert
		assertDoesNotThrow(() -> listAllTasks.execute(params));
	}

	@ParameterizedTest(name = "passed arguments: {0}")
	@ValueSource(strings = {"sortByTitle"})
	public void execute_Should_ListAllTasks_When_ValidSortParametersPassed(String argument) {
		// Arrange
		List<String> params = List.of(argument);

		// Act, Assert
		assertDoesNotThrow(() -> listAllTasks.execute(params));
	}
	@ParameterizedTest(name = "passed arguments: {0}")
	@ValueSource(strings = {"sortByPriority", "sortBySeverity", "sortByRating", "sortBySize"})
	public void execute_Should_ThrowException_When_InvalidSortParametersPassed(String argument) {
		// Arrange
		List<String> params = List.of(argument);

		// Act, Assert
		assertThrows(InvalidUserInputException.class, () -> listAllTasks.execute(params));
	}
	@Test
	public void execute_Should_ListAllTasks_When_ZeroParametersSpecified() {
		// Arrange
		List<String> params = List.of();

		// Act, Assert
		assertDoesNotThrow(() -> listAllTasks.execute(params));
	}

}
