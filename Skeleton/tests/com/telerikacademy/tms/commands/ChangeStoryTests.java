package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.tasks.contracts.Story;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;
import com.telerikacademy.tms.models.tasks.enums.SizeType;
import com.telerikacademy.tms.models.tasks.enums.StoryStatus;
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

public class ChangeStoryTests {
    private static final int EXPECTED_NUMBER_PARAMETERS = 3;
    private Command changeStory;
    private TaskManagementRepository repository;

    @BeforeEach
    public void before() {
        repository = new TaskManagementRepositoryImpl();
        changeStory = new ChangeStory(repository);
    }

    @ParameterizedTest(name = "passed arguments: {0}")
    @ValueSource(ints = {EXPECTED_NUMBER_PARAMETERS - 1, EXPECTED_NUMBER_PARAMETERS + 1})
    public void execute_Should_ThrowException_When_ArgumentsCountDiffer(int argumentsCount) {
        // Arrange
        List<String> parameters = getList(argumentsCount);

        //Act, Assert
        assertThrows(IllegalArgumentException.class, () -> changeStory.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_WrongChangeParameterProvided() {
        // Arrange
        Story story = repository.createStory(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.MEDIUM, SizeType.LARGE);

        // Act, Assert
        assertThrows(UnsupportedOperationException.class,
                () -> changeStory.execute(List.of(valueOf(story.getID()), "severity", valueOf(SeverityType.MAJOR))));
    }

    @Test
    public void execute_Should_ThrowException_When_EnumParameterDoesNotMatch() {
        // Arrange
        Story story = repository.createStory(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.MEDIUM, SizeType.LARGE);

        // Act, Assert
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> changeStory.execute(List.of(valueOf(story.getID()), "status", valueOf(PriorityType.HIGH)))),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> changeStory.execute(List.of(valueOf(story.getID()), "size", valueOf(StoryStatus.DONE)))),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> changeStory.execute(List.of(valueOf(story.getID()), "priority", valueOf(SizeType.SMALL))))
        );
    }

    @Test
    public void execute_Should_ThrowException_When_ChangingToSameParameter() {
        // Arrange
        Story story = repository.createStory(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.MEDIUM, SizeType.LARGE);

        // Act, Assert
        assertAll(
                () -> assertThrows(InvalidUserInputException.class,
                        () -> changeStory.execute(List.of(valueOf(story.getID()), "status", valueOf(StoryStatus.NOT_DONE)))),
                () -> assertThrows(InvalidUserInputException.class,
                        () -> changeStory.execute(List.of(valueOf(story.getID()), "priority", valueOf(PriorityType.MEDIUM)))),
                () -> assertThrows(InvalidUserInputException.class,
                        () -> changeStory.execute(List.of(valueOf(story.getID()), "size", valueOf(SizeType.LARGE))))
        );
    }

    @Test
    public void execute_Should_ChangeParameters_When_ValidArgumentsPassed() {
        // Arrange
        Story story = repository.createStory(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.MEDIUM, SizeType.LARGE);

        // Act
        changeStory.execute(List.of(valueOf(story.getID()), "status", valueOf(StoryStatus.INPROGRESS)));
        changeStory.execute(List.of(valueOf(story.getID()), "priority", valueOf(PriorityType.LOW)));
        changeStory.execute(List.of(valueOf(story.getID()), "size", valueOf(SizeType.MEDIUM)));

        // Assert
        assertAll(
                () -> assertEquals(StoryStatus.INPROGRESS, story.getStatus()),
                () -> assertEquals(PriorityType.LOW, story.getPriority()),
                () -> assertEquals(SizeType.MEDIUM, story.getSize())
        );
    }
}
