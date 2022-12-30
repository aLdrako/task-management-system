package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.ElementNotFoundException;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.contracts.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.tms.commands.ShowAllTeamBoards.BOARDS_LISTED;
import static com.telerikacademy.tms.commands.ShowAllTeamMembers.NO_MEMBERS_LISTED;
import static com.telerikacademy.tms.commands.ShowAllTeamMembers.EXPECTED_NUMBER_PARAMETERS;
import static com.telerikacademy.tms.commands.ShowAllTeamMembers.MEMBERS_LISTED;
import static com.telerikacademy.tms.utils.TestUtils.getList;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

class ShowAllTeamMembersTests {

    private TaskManagementRepository repository;
    private Command command;

    @BeforeEach
    void setUp() {
        repository = new TaskManagementRepositoryImpl();
        command = new ShowAllTeamMembers(repository);

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
        parameters.add("Non existant member");
        // Act, Assert
        Assertions.assertThrows(ElementNotFoundException.class, () -> command.execute(parameters));
    }

    @Test
    void execute_Should_ReturnNoMemberMessageForExistingTeam() {
        // Arrange
//        User user = repository.createUser("User 01");
        Team team = repository.createTeam("Team 01");
//        team.addUser(user);
        List<String> parameters = new ArrayList<String>();
        parameters.add("Team 01");
        // Act
        String result = command.execute(parameters);
        // Assert
        Assertions.assertEquals(format(NO_MEMBERS_LISTED, "Team 01"), result);
    }
    @Test
    void execute_Should_ReturnStringContainingMembersForExistingTeam() {
        // Arrange
        User user = repository.createUser("User 02");
        Team team = repository.createTeam("Team 02");
        team.addUser(user);
        List<String> parameters = new ArrayList<String>();
        parameters.add("Team 02");
        // Act
        String result = command.execute(parameters);
        // Assert
        int index  = result.indexOf(format(MEMBERS_LISTED, "Team 02", 1));
        Assertions.assertEquals(0, index);
    }
}