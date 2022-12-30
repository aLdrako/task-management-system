package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
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

import static com.telerikacademy.tms.commands.ListAllTasks.LISTING_HEADER;
import static com.telerikacademy.tms.utils.ListingHelpers.elementsToString;
import static com.telerikacademy.tms.utils.ListingHelpers.listingCommandsSubHeader;
import static com.telerikacademy.tms.utils.ModelsConstants.DESCRIPTION_VALID_NAME;
import static com.telerikacademy.tms.utils.ModelsConstants.TASK_VALID_NAME;
import static com.telerikacademy.tms.utils.TestUtils.getList;
import static java.lang.String.format;

public class ListAllTasksTests {
    private TaskManagementRepository repository;
    private Command command;


    @BeforeEach
    public void before() {
        repository = new TaskManagementRepositoryImpl();
        command = new ListAllTasks(repository);
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
                () -> Assertions.assertThrows(InvalidUserInputException.class, () -> command.execute(parameters)),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters1))
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
                () -> Assertions.assertDoesNotThrow(() -> command.execute(parameters)),
                () -> Assertions.assertDoesNotThrow(() -> command.execute(parameters1))
        );
    }

    @Test
    public void execute_Should_printAllTasks_When_LastParameterIsSorting() {
        // Arrange
        repository.createStory(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SizeType.LARGE);
        repository.createFeedback(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, Rating.FIVE);
        repository.createBug(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SeverityType.MINOR, getList(1));
        List<String> parameters = getList(0);

        Assertions.assertEquals(format(LISTING_HEADER, listingCommandsSubHeader(parameters), elementsToString(repository.getTasks())),
                command.execute(parameters));
    }

}
