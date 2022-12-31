package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Story;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SizeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.telerikacademy.tms.utils.ModelsConstants.*;
import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.*;

public class ListAllStoriesTests {
	private TaskManagementRepository repository;
	private Command listAllStories;

	@BeforeEach
	public void before() {
		repository = new TaskManagementRepositoryImpl();
		listAllStories = new ListAllStories(repository);
	}

	@ParameterizedTest(name = "passed arguments: {0}")
	@ValueSource(strings = {"sortByTitle", "filterByStatus"})
	public void execute_Should_ThrowException_When_ReceivingParametersAfterSorting(String argument) {
		// Arrange
		Story story = repository.createStory(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SizeType.SMALL);
		List<String> params1 = List.of("sortByTitle", argument);
		List<String> params2 = List.of("filterByStatus", valueOf(story.getStatus()), "sortByTitle", argument);

		// Act, Assert
		assertAll(
				() -> assertThrows(InvalidUserInputException.class, () -> listAllStories.execute(params1)),
				() -> assertThrows(IllegalArgumentException.class, () -> listAllStories.execute(params2))
		);
	}

	@ParameterizedTest(name = "passed arguments: {0}")
	@ValueSource(strings = {"filterByStatus", "filterByAssignee", "filterByStatusAndAssignee"})
	public void execute_Should_ThrowException_When_FilterArgumentsCountDiffer(String argument) {
		// Arrange
		List<String> params = List.of(argument);

		// Act, Assert
		assertThrows(InvalidUserInputException.class, () -> listAllStories.execute(params));
	}

	@Test
	public void execute_Should_ThrowException_When_WrongFilterParameterSpecified() {
		// Arrange
		List<String> params = List.of("filterBySomething");

		// Act, Assert
		assertThrows(InvalidUserInputException.class, () -> listAllStories.execute(params));
	}

	@Test
	public void execute_Should_ThrowException_When_WrongSortParameterSpecified() {
		// Arrange
		List<String> params = List.of("sortBySomething");

		// Act, Assert
		assertThrows(InvalidUserInputException.class, () -> listAllStories.execute(params));
	}

	@Test
	public void execute_Should_ListAllStories_When_ValidFilterParametersPassed() {
		// Arrange
		Story story = repository.createStory(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SizeType.SMALL);
		User user = repository.createUser(USER_VALID_NAME);
		story.setAssignee(user);
		List<String> params1 = List.of("filterByStatus", valueOf(story.getStatus()));
		List<String> params2 = List.of("filterByAssignee", user.getName());
		List<String> params3 = List.of("filterByStatusAndAssignee", valueOf(story.getStatus()), user.getName());

		// Act, Assert
		assertAll(
				() -> assertDoesNotThrow(() -> listAllStories.execute(params1)),
				() -> assertDoesNotThrow(() -> listAllStories.execute(params2)),
				() -> assertDoesNotThrow(() -> listAllStories.execute(params3))
		);
	}

	@ParameterizedTest(name = "passed arguments: {0}")
	@ValueSource(strings = {"sortByTitle", "sortByPriority", "sortBySize"})
	public void execute_Should_ListAllStories_When_ValidSortParametersPassed(String argument) {
		// Arrange
		List<String> params = List.of(argument);

		// Act, Assert
		assertDoesNotThrow(() -> listAllStories.execute(params));
	}

	@Test
	public void execute_Should_ListAllStories_When_LastParameterIsSorting() {
		// Arrange
		Story story = repository.createStory(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SizeType.SMALL);
		List<String> params1 = List.of("sortByTitle");
		List<String> params2 = List.of("filterByStatus", valueOf(story.getStatus()), "sortByTitle");

		// Act, Assert
		assertAll(
				() -> assertDoesNotThrow(() -> listAllStories.execute(params1)),
				() -> assertDoesNotThrow(() -> listAllStories.execute(params2))
		);
	}

	@Test
	public void execute_Should_ListAllStories_When_ZeroParametersSpecified() {
		// Arrange
		List<String> params = List.of();

		// Act, Assert
		assertDoesNotThrow(() -> listAllStories.execute(params));
	}
}
