package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.ElementNotFoundException;
import com.telerikacademy.tms.models.BoardImpl;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static com.telerikacademy.tms.commands.CreateTaskInBoard.TASK_CREATED_SUCCESSFULLY;
import static java.lang.String.format;

class CreateTaskInBoardTests {

    private TaskManagementRepository repository;
    private Command command;

    @BeforeEach
    void setUp() {
        repository = new TaskManagementRepositoryImpl();
        command = new CreateTaskInBoard(repository);
    }
    private void addFakeParameters(List<String> parameters, int count) {
        for (int i = 0; i < count; i++) {
            parameters.add("Fake " + i);
        }
    }

    @Test
    void execute_Should_ThrowException_When_NumberOfArgumentsIsInvalidForBugTask() {
        // Arrange
        List<String> parameters = new ArrayList<String>();
        parameters.add("Bug");
        addFakeParameters(parameters, 6);
        Team team = repository.createTeam("Fake 1");
        Board board = new BoardImpl("Fake 0");
        team.addBoard(board);
        // Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }



    @Test
    void execute_Should_ThrowException_When_NumberOfArgumentsIsInvalidForStoryTask() {
        // Arrange
        List<String> parameters = new ArrayList<String>();
        parameters.add("Story");
        addFakeParameters(parameters, 5);
        Team team = repository.createTeam("Fake 1");
        Board board = new BoardImpl("Fake 0");
        team.addBoard(board);
        // Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
        addFakeParameters(parameters, 2);
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }
    @Test
    void execute_Should_ThrowException_When_NumberOfArgumentsIsInvalidForFeedbackTask() {
        // Arrange
        List<String> parameters = new ArrayList<String>();
        parameters.add("Feedback");
        addFakeParameters(parameters, 4);
        Team team = repository.createTeam("Fake 1");
        Board board = new BoardImpl("Fake 0");
        team.addBoard(board);
        // Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
        addFakeParameters(parameters, 2);
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }
    @Test
    void execute_Should_ThrowException_When_TeamDoesNotExist() {
        // Arrange
        List<String> parameters = new ArrayList<String>();
        parameters.add("Feedback");
        addFakeParameters(parameters, 5);
        // Act, Assert
        Assertions.assertThrows(ElementNotFoundException.class, () -> command.execute(parameters));
    }
    @Test
    void execute_Should_ThrowException_When_BoardDoesNotExist() {
        // Arrange
        List<String> parameters = new ArrayList<String>();
        parameters.add("Feedback");
        addFakeParameters(parameters, 5);
        Team team = repository.createTeam("Fake 1");
        // Act, Assert
        Assertions.assertThrows(ElementNotFoundException.class, () -> command.execute(parameters));
    }
    @Test
    void execute_Should_ReturnCorrectResultForFeedback() {
        // Arrange
        List<String> parameters = new ArrayList<String>();
        parameters.add("Feedback");
        parameters.add("Fake 0");
        parameters.add("Fake 1");
        parameters.add("Title For Testing Purposes");
        parameters.add("Description For Testing");
        parameters.add("1");
        Team team = repository.createTeam("Fake 1");
        Board board = new BoardImpl("Fake 0");
        team.addBoard(board);
        // Act
        String result = command.execute(parameters);
        int index = result.indexOf(format(TASK_CREATED_SUCCESSFULLY, "Feedback", "Title For Testing Purposes", 1, board.getName()));
        //Assert
        Assertions.assertEquals(0, index);
    }
    @Test
    void execute_Should_ReturnCorrectResultForBug() {
        // Arrange
        List<String> parameters = new ArrayList<String>();
        parameters.add("Bug");
        parameters.add("Fake 0");
        parameters.add("Fake 1");
        parameters.add("Title For Testing Purposes");
        parameters.add("Description For Testing");
        parameters.add("High");
        parameters.add("Critical");
        parameters.add("Step 1");
        Team team = repository.createTeam("Fake 1");
        Board board = new BoardImpl("Fake 0");
        team.addBoard(board);
        // Act
        String result = command.execute(parameters);
        int index = result.indexOf(format(TASK_CREATED_SUCCESSFULLY, "Bug", "Title For Testing Purposes", 1, board.getName()));
        //Assert
        Assertions.assertEquals(0, index);
    }
    @Test
    void execute_Should_ReturnCorrectResultForStory() {
        // Arrange
        List<String> parameters = new ArrayList<String>();
        parameters.add("Story");
        parameters.add("Fake 0");
        parameters.add("Fake 1");
        parameters.add("Title For Testing Purposes");
        parameters.add("Description For Testing");
        parameters.add("High");
        parameters.add("Large");
        Team team = repository.createTeam("Fake 1");
        Board board = new BoardImpl("Fake 0");
        team.addBoard(board);
        // Act
        String result = command.execute(parameters);
        int index = result.indexOf(format(TASK_CREATED_SUCCESSFULLY, "Story", "Title For Testing Purposes", 1, board.getName()));
        //Assert
        Assertions.assertEquals(0, index);
    }

}