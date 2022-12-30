package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.DuplicateElementException;
import com.telerikacademy.tms.exceptions.ElementNotFoundException;
import com.telerikacademy.tms.models.BoardImpl;
import com.telerikacademy.tms.models.TeamImpl;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static com.telerikacademy.tms.commands.CreateBoardInTeam.EXPECTED_NUMBER_PARAMETERS;
import static com.telerikacademy.tms.commands.CreateBoardInTeam.BOARD_CREATED_SUCCESSFULLY;
import static com.telerikacademy.tms.utils.TestUtils.getList;
import static java.lang.String.format;

class CreateBoardInTeamTests {

    private TaskManagementRepository repository;
    private Command command;

    @BeforeEach
    void setUp() {
        repository = new TaskManagementRepositoryImpl();
        command = new CreateBoardInTeam(repository);
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
    void execute_Should_ThrowException_When_TryToCreateBoardInTeamThatDoesNotExist() {
        // Arrange
        Board board = new BoardImpl("Board 01");
        List<String> parameters = new ArrayList<String>();
        parameters.add("Board 01");
        parameters.add("Team 01");

        //Act, Assert
        Assertions.assertThrows(ElementNotFoundException.class, () -> command.execute(parameters));
    }
    @Test
    void execute_Should_Throw_Exception_When_ReceivingDuplicateBoardName(){
        //Arrange
        Board board = new BoardImpl("Board 01");
        Team team = repository.createTeam("Team 01");
        team.addBoard(board);
        List<String> parameters = new ArrayList<String>();
        parameters.add("Board 01");
        parameters.add("Team 01");
        //Act, Assert
        Assertions.assertThrows(DuplicateElementException.class, () -> command.execute(parameters));
    }
    @Test
    void execute_Should_CreateBoardInTeamWhenReceivingValidArguments() {
        Team team = repository.createTeam("Team 01");
        List<String> parameters = new ArrayList<String>();
        parameters.add("Board 01");
        parameters.add("Team 01");
        // Act
        String result = command.execute(parameters);
        // Assert
        int index  = result.indexOf(format(BOARD_CREATED_SUCCESSFULLY, "Board 01", "Team 01"));
        Assertions.assertEquals(0, index);

    }
}