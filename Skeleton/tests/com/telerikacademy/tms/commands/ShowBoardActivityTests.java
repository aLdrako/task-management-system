package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.ElementNotFoundException;
import com.telerikacademy.tms.models.BoardImpl;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.tasks.BugImpl;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.tms.commands.ShowBoardActivity.EXPECTED_NUMBER_PARAMETERS;
import static com.telerikacademy.tms.commands.ShowBoardActivity.ACTIVITY_HISTORY_BOARD_HEADER;
import static com.telerikacademy.tms.utils.ListingHelpers.activityListing;
import static com.telerikacademy.tms.utils.TestUtils.getList;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

class ShowBoardActivityTests {

    private TaskManagementRepository repository;
    private Command command;

    @BeforeEach
    void setUp() {
        repository = new TaskManagementRepositoryImpl();
        command = new ShowBoardActivity(repository);
    }

    @Test
    void execute_Should_ThrowException_When_NumberOfArgumentsIsInvalid() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_PARAMETERS + 1);

        // Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));

        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(getList(EXPECTED_NUMBER_PARAMETERS - 1)));
    }

    @Test
    void execute_Should_ThrowException_When_GivenBoardDoesNotExist() {
        // Arrange
        List<String> parameters = new ArrayList<String>();
        parameters.add("Non existing team");
        parameters.add("Non existing board");
        // Act, Assert
        Assertions.assertThrows(ElementNotFoundException.class, () -> command.execute(parameters));
    }

    @Test
    void execute_Should_ReturnBoardActivities() {
        //Arrange
        Team team = repository.createTeam("Team 01");
        Board board = new BoardImpl("Board 01");
        team.addBoard(board);
        board.addTask(new BugImpl(1 , "New Bug to be fixed as soon as possible" , "For testing purposes of Unit test", PriorityType.LOW, SeverityType.MINOR, new ArrayList<>()));
        List<String> parameters = new ArrayList<String>();
        parameters.add("Board 01");
        parameters.add("Team 01");
        //Act
        String result = command.execute(parameters);
        int index = result.indexOf(format(ACTIVITY_HISTORY_BOARD_HEADER, team.getName(),
                board.getName()) +
                System.lineSeparator() +
                activityListing(board.getHistories()));
        //Assert
        Assertions.assertEquals(0, index);

    }


}