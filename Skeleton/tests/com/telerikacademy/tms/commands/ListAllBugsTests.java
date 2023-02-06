package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Bug;
import com.telerikacademy.tms.models.tasks.contracts.Task;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.telerikacademy.tms.utils.ModelsConstants.*;
import static com.telerikacademy.tms.utils.TestUtils.getList;
import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.*;

public class ListAllBugsTests {
    private TaskManagementRepository repository;
    private Command listAllBugs;

    @BeforeEach
    public void before() {
        repository = new TaskManagementRepositoryImpl();
        listAllBugs = new ListAllBugs(repository);
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
                () -> assertThrows(InvalidUserInputException.class, () -> listAllBugs.execute(params1)),
                () -> assertThrows(IllegalArgumentException.class, () -> listAllBugs.execute(params2))
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
                () -> Assertions.assertThrows(InvalidUserInputException.class, () -> listAllBugs.execute(parameters)),
                () -> Assertions.assertThrows(InvalidUserInputException.class, () -> listAllBugs.execute(parameters1)),
                () -> Assertions.assertThrows(InvalidUserInputException.class, () -> listAllBugs.execute(parameters2)),
                () -> Assertions.assertThrows(InvalidUserInputException.class, () -> listAllBugs.execute(parameters3))
        );
    }

    @ParameterizedTest(name = "passed arguments: {0}")
    @ValueSource(strings = {"filterByStatus", "filterByAssignee", "filterByStatusAndAssignee"})
    public void execute_Should_ThrowException_When_FilterArgumentsCountDiffer(String argument) {
        // Arrange
        List<String> params = List.of(argument);

        // Act, Assert
        assertThrows(InvalidUserInputException.class, () -> listAllBugs.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_WrongParameterSpecified() {
        // Arrange
        List<String> params1 = List.of("sortBySomething");
        List<String> params2 = List.of("filterBySomething");

        // Act, Assert
        assertAll(
                () -> assertThrows(InvalidUserInputException.class, () -> listAllBugs.execute(params1)),
                () -> assertThrows(InvalidUserInputException.class, () -> listAllBugs.execute(params2))
        );
    }

    @Test
    public void execute_Should_ListAllBugs_When_ValidFilterParametersPassed() {
        // Arrange
        Bug bug = repository.createBug(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SeverityType.MINOR, List.of("Step 1", "Step 2"));
        User user = repository.createUser(USER_VALID_NAME);
        bug.setAssignee(user);
        List<String> params1 = List.of("filterByStatus", valueOf(bug.getStatus()));
        List<String> params2 = List.of("filterByAssignee", user.getName());
        List<String> params3 = List.of("filterByStatusAndAssignee", valueOf(bug.getStatus()), user.getName());

        // Act, Assert
        assertAll(
                () -> assertDoesNotThrow(() -> listAllBugs.execute(params1)),
                () -> assertDoesNotThrow(() -> listAllBugs.execute(params2)),
                () -> assertDoesNotThrow(() -> listAllBugs.execute(params3))
        );
    }

    @ParameterizedTest(name = "passed arguments: {0}")
    @ValueSource(strings = {"sortByTitle", "sortByPriority", "sortBySeverity"})
    public void execute_Should_ListAllBugs_When_ValidSortParametersPassed(String argument) {
        // Arrange
        List<String> params = List.of(argument);

        // Act, Assert
        assertDoesNotThrow(() -> listAllBugs.execute(params));
    }

    @ParameterizedTest(name = "passed arguments: {0}")
    @ValueSource(strings = {"sortByRating", "sortBySize"})
    public void execute_Should_ThrowException_When_InvalidSortParametersPassed(String argument) {
        // Arrange
        List<String> params = List.of(argument);

        // Act, Assert
        assertThrows(InvalidUserInputException.class, () -> listAllBugs.execute(params));
    }

    @Test
    public void execute_Should_ListAllBugs_When_LastParameterIsSorting() {
        // Arrange
        Bug bug = repository.createBug(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SeverityType.MINOR, List.of("Step 1", "Step 2"));
        List<String> params1 = List.of("sortByTitle");
        List<String> params2 = List.of("filterByStatus", valueOf(bug.getStatus()), "sortByTitle");

        // Act, Assert
        assertAll(
                () -> assertDoesNotThrow(() -> listAllBugs.execute(params1)),
                () -> assertDoesNotThrow(() -> listAllBugs.execute(params2))
        );
    }

    @Test
    public void execute_Should_ListAllBugs_When_ZeroParametersSpecified() {
        // Arrange
        List<String> params = List.of();

        // Act, Assert
        assertDoesNotThrow(() -> listAllBugs.execute(params));
    }
}