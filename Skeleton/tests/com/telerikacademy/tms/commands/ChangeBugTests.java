package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.tasks.contracts.Bug;
import com.telerikacademy.tms.models.tasks.enums.BugStatus;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;
import com.telerikacademy.tms.models.tasks.enums.SizeType;
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

public class ChangeBugTests {
    private static final int EXPECTED_NUMBER_PARAMETERS = 3;
    private Command changeBug;
    private TaskManagementRepository repository;

    @BeforeEach
    public void before() {
        repository = new TaskManagementRepositoryImpl();
        changeBug = new ChangeBug(repository);
    }

    @ParameterizedTest(name = "passed arguments: {0}")
    @ValueSource(ints = {EXPECTED_NUMBER_PARAMETERS - 1, EXPECTED_NUMBER_PARAMETERS + 1})
    public void execute_Should_ThrowException_When_ArgumentsCountDiffer(int argumentsCount) {
        // Arrange
        List<String> parameters = getList(argumentsCount);

        //Act, Assert
        assertThrows(IllegalArgumentException.class, () -> changeBug.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_WrongChangeParameterProvided() {
        // Arrange
        Bug bug = repository.createBug(TASK_VALID_NAME, DESCRIPTION_VALID_NAME,
                PriorityType.MEDIUM, SeverityType.CRITICAL, List.of("Step 1", "Step 2"));

        // Act, Assert
        assertThrows(UnsupportedOperationException.class,
                () -> changeBug.execute(List.of(valueOf(bug.getID()), "size", valueOf(SizeType.LARGE))));
    }

    @Test
    public void execute_Should_ThrowException_When_EnumParameterDoesNotMatch() {
        // Arrange
        Bug bug = repository.createBug(TASK_VALID_NAME, DESCRIPTION_VALID_NAME,
                PriorityType.MEDIUM, SeverityType.CRITICAL, List.of("Step 1", "Step 2"));

        // Act, Assert
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> changeBug.execute(List.of(valueOf(bug.getID()), "status", valueOf(PriorityType.HIGH)))),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> changeBug.execute(List.of(valueOf(bug.getID()), "severity", valueOf(BugStatus.FIXED)))),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> changeBug.execute(List.of(valueOf(bug.getID()), "priority", valueOf(SeverityType.MAJOR))))
        );
    }

    @Test
    public void execute_Should_ThrowException_When_ChangingToSameParameter() {
        // Arrange
        Bug bug = repository.createBug(TASK_VALID_NAME, DESCRIPTION_VALID_NAME,
                PriorityType.MEDIUM, SeverityType.CRITICAL, List.of("Step 1", "Step 2"));

        // Act, Assert
        assertAll(
                () -> assertThrows(InvalidUserInputException.class,
                        () -> changeBug.execute(List.of(valueOf(bug.getID()), "status", valueOf(BugStatus.ACTIVE)))),
                () -> assertThrows(InvalidUserInputException.class,
                        () -> changeBug.execute(List.of(valueOf(bug.getID()), "priority", valueOf(PriorityType.MEDIUM)))),
                () -> assertThrows(InvalidUserInputException.class,
                        () -> changeBug.execute(List.of(valueOf(bug.getID()), "severity", valueOf(SeverityType.CRITICAL))))
        );
    }

    @Test
    public void execute_Should_ChangeParameters_When_ValidArgumentsPassed() {
        // Arrange
        Bug bug = repository.createBug(TASK_VALID_NAME, DESCRIPTION_VALID_NAME,
                PriorityType.MEDIUM, SeverityType.CRITICAL, List.of("Step 1", "Step 2"));

        // Act
        changeBug.execute(List.of(valueOf(bug.getID()), "status", valueOf(BugStatus.FIXED)));
        changeBug.execute(List.of(valueOf(bug.getID()), "priority", valueOf(PriorityType.LOW)));
        changeBug.execute(List.of(valueOf(bug.getID()), "severity", valueOf(SeverityType.MINOR)));

        // Assert
        assertAll(
                () -> assertEquals(BugStatus.FIXED, bug.getStatus()),
                () -> assertEquals(PriorityType.LOW, bug.getPriority()),
                () -> assertEquals(SeverityType.MINOR, bug.getSeverity())
        );
    }
}
