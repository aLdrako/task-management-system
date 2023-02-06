package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.UserImpl;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Assignable;
import com.telerikacademy.tms.models.tasks.contracts.Bug;
import com.telerikacademy.tms.models.tasks.contracts.Story;
import com.telerikacademy.tms.models.tasks.contracts.Task;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;
import com.telerikacademy.tms.models.tasks.enums.SizeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Collectors;

import static com.telerikacademy.tms.utils.ListingHelpers.elementsToString;
import static com.telerikacademy.tms.utils.ListingHelpers.listingCommandsSubHeader;
import static com.telerikacademy.tms.utils.ModelsConstants.*;
import static com.telerikacademy.tms.utils.TestUtils.getList;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.*;

class ListTasksWithAssigneeTests {
    private static final String LISTING_HEADER = "LIST TASKS WITH ASSIGNEE %s %n%s";
    private TaskManagementRepository repository;
    private Command listTasksWithAssignee;

    @BeforeEach
    public void before() {
        repository = new TaskManagementRepositoryImpl();
        listTasksWithAssignee = new ListTasksWithAssignee(repository);
    }

    @ParameterizedTest(name = "passed arguments: {0}")
    @ValueSource(strings = {"sortByTitle", "filterByStatus"})
    public void execute_Should_ThrowException_When_ReceivingParametersAfterSorting(String argument) {
        // Arrange
        Bug bug = repository.createBug(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SeverityType.MINOR, List.of("Step 1", "Step 2"));
        List<String> params1 = List.of("sortByTitle", argument);
        List<String> params2 = List.of("filterByStatus", valueOf(bug.getStatus()), "sortByTitle", argument);

        // Act, Assert
        assertAll(
                () -> assertThrows(InvalidUserInputException.class, () -> listTasksWithAssignee.execute(params1)),
                () -> assertThrows(IllegalArgumentException.class, () -> listTasksWithAssignee.execute(params2))
        );
    }

    @Test
    public void execute_Should_ThrowException_When_ReceivingInvalidArguments() {
        // Arrange
        Task task = repository.createBug(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SeverityType.MINOR,
                getList(1));
        List<String> parameters = List.of(RANDOM_WORD);
        List<String> parameters1 = List.of(RANDOM_WORD, "filterByStatus", task.getStatus().toString(), "sortByTitle");
        List<String> parameters2 = List.of(RANDOM_WORD, "filterByStatus", task.getStatus().toString());
        List<String> parameters3 = List.of(RANDOM_WORD, "sortByTitle");

        // Act, Assert
        Assertions.assertAll(
                () -> Assertions.assertThrows(InvalidUserInputException.class, () -> listTasksWithAssignee.execute(parameters)),
                () -> Assertions.assertThrows(InvalidUserInputException.class, () -> listTasksWithAssignee.execute(parameters1)),
                () -> Assertions.assertThrows(InvalidUserInputException.class, () -> listTasksWithAssignee.execute(parameters2)),
                () -> Assertions.assertThrows(InvalidUserInputException.class, () -> listTasksWithAssignee.execute(parameters3))
        );
    }

    @ParameterizedTest(name = "passed arguments: {0}")
    @ValueSource(strings = {"filterByStatus", "filterByAssignee", "filterByStatusAndAssignee"})
    public void execute_Should_ThrowException_When_FilterArgumentsCountDiffer(String argument) {
        // Arrange
        List<String> params = List.of(argument);

        // Act, Assert
        assertThrows(InvalidUserInputException.class, () -> listTasksWithAssignee.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_WrongParameterSpecified() {
        // Arrange
        List<String> params1 = List.of("sortBySomething");
        List<String> params2 = List.of("filterBySomething");

        // Act, Assert
        assertAll(
                () -> assertThrows(InvalidUserInputException.class, () -> listTasksWithAssignee.execute(params1)),
                () -> assertThrows(InvalidUserInputException.class, () -> listTasksWithAssignee.execute(params2))
        );
    }

    @Test
    public void execute_Should_ListAllTasksWithAssignee_When_ValidFilterParametersPassed() {
        // Arrange
        Bug bug = repository.createBug(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SeverityType.MINOR, List.of("Step 1", "Step 2"));
        repository.createStory(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SizeType.LARGE);
        User user = repository.createUser(USER_VALID_NAME);
        bug.setAssignee(user);
        List<Assignable> tasks = repository.getAssignableTasks().stream()
                .filter(task -> !task.getAssignee().getName().equalsIgnoreCase("Unassigned"))
                .collect(Collectors.toList());
        List<String> params1 = List.of("filterByStatus", valueOf(bug.getStatus()));
        List<String> params2 = List.of("filterByAssignee", user.getName());
        List<String> params3 = List.of("filterByStatusAndAssignee", valueOf(bug.getStatus()), user.getName());

        // Act, Assert
        assertAll(
                () -> assertDoesNotThrow(() -> listTasksWithAssignee.execute(params1)),
                () -> assertDoesNotThrow(() -> listTasksWithAssignee.execute(params2)),
                () -> assertDoesNotThrow(() -> listTasksWithAssignee.execute(params3)),
                () -> assertEquals(format(LISTING_HEADER, listingCommandsSubHeader(List.of()), elementsToString(tasks)),
                        listTasksWithAssignee.execute(List.of()))
        );
    }

    @ParameterizedTest(name = "passed arguments: {0}")
    @ValueSource(strings = {"sortByTitle"})
    public void execute_Should_ListAllTasksWithAssignee_When_ValidSortParametersPassed(String argument) {
        // Arrange
        List<String> params = List.of(argument);

        // Act, Assert
        assertDoesNotThrow(() -> listTasksWithAssignee.execute(params));
    }

    @ParameterizedTest(name = "passed arguments: {0}")
    @ValueSource(strings = {"sortByRating", "sortBySize", "sortByPriority", "sortBySeverity"})
    public void execute_Should_ThrowException_When_InvalidSortParametersPassed(String argument) {
        // Arrange
        List<String> params = List.of(argument);

        // Act, Assert
        assertThrows(InvalidUserInputException.class, () -> listTasksWithAssignee.execute(params));
    }

    @Test
    public void execute_Should_ListAllTasksWIthAssignee_When_LastParameterIsSorting() {
        // Arrange
        User user = new UserImpl(USER_VALID_NAME);
        Bug bug = repository.createBug(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SeverityType.MINOR, List.of("Step 1", "Step 2"));
        Story story = repository.createStory(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SizeType.LARGE);
        bug.setAssignee(user);
        story.setAssignee(user);
        List<String> params1 = List.of("sortByTitle");
        List<String> params2 = List.of("filterByStatus", valueOf(bug.getStatus()), "sortByTitle");
        List<String> params3 = List.of("filterByStatus", valueOf(story.getStatus()), "sortByTitle");

        // Act, Assert
        assertAll(
                () -> assertDoesNotThrow(() -> listTasksWithAssignee.execute(params1)),
                () -> assertDoesNotThrow(() -> listTasksWithAssignee.execute(params2)),
                () -> assertDoesNotThrow(() -> listTasksWithAssignee.execute(params3))
        );
    }

    @Test
    public void execute_Should_ListAllTasksWIthAssignee_When_ZeroParametersSpecified() {
        // Arrange
        List<String> params = List.of();

        // Act, Assert
        assertDoesNotThrow(() -> listTasksWithAssignee.execute(params));
    }
}