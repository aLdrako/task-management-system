package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.BoardImpl;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Task;
import com.telerikacademy.tms.models.tasks.enums.BugStatus;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.tms.commands.ShowAllTeamMembers.EXPECTED_NUMBER_PARAMETERS;
import static com.telerikacademy.tms.utils.TestUtils.getList;
import static org.junit.jupiter.api.Assertions.*;

class ListTasksWithAssigneeTests {

    private TaskManagementRepository repository;
    private Command command;

    @BeforeEach
    void setUp() {
        repository = new TaskManagementRepositoryImpl();
        command = new ListTasksWithAssignee(repository);
        AssignTask assignTaskCommand= new AssignTask(repository);
        User user1 = repository.createUser("User 01");
        Task task1 = repository.createBug("New Bug 2 For Testing", "Description for testing", PriorityType.LOW, SeverityType.MINOR,  new ArrayList<String>());
        Task task2 = repository.createBug("New Bug 1 For Testing", "Description for testing", PriorityType.MEDIUM, SeverityType.MINOR,  new ArrayList<String>());
        task2.setStatus(BugStatus.FIXED);
        User user2 = repository.createUser("User 02");
        Task task3 = repository.createBug("New Bug 3 For Testing", "Description for testing", PriorityType.MEDIUM, SeverityType.MINOR,  new ArrayList<String>());
        // ==================================================
        Team team = repository.createTeam("Fake team 01");
        team.addUser(user1);
        team.addUser(user2);
        Board board = new BoardImpl("Fake board");
        team.addBoard(board);
        board.addTask(task1);
        board.addTask(task2);
        board.addTask(task3);
        // ==================================================
        List<String> params = new ArrayList<>();
        params.add(String.valueOf(task1.getID()));
        params.add("User 01");
        assignTaskCommand.execute(params);
        params.set(0,String.valueOf(task2.getID()));
        assignTaskCommand.execute(params);
        params.set(0,String.valueOf(task3.getID()));
        params.set(1,"User 02");
        assignTaskCommand.execute(params);
    }

    @Test
    void execute_Should_ReturnEmptyListForNonExistentUser() {
        // Arrange
        List<String> parameters = new ArrayList<String>();
        parameters.add("FilterByAssignee");
        parameters.add("Fake User");

        // Act
        String result = command.execute(parameters);
        //Assert
        int index = result.indexOf("=== EMPTY LIST ===");
        Assertions.assertNotEquals(-1,index);
    }
    @Test
    void execute_Should_ThrowException_When_StatusIsNotValid() {
        // Arrange
        List<String> parameters = new ArrayList<String>();
        parameters.add("FilterByStatus");
        parameters.add("Fake Status");

        // Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }
    @Test
    void execute_Should_ReturnFilteredByAssigneeTasks() {
        // Arrange
        List<String> parameters = new ArrayList<String>();
        parameters.add("FilterByAssignee");
        parameters.add("User 01");

        // Act
        String result = command.execute(parameters);
        // Assert
        int index1 = result.indexOf("Bug: ID -> [1] 'New Bug 2 For Testing' | Status: Active | Priority: Low | Severity: Minor | Assignee: User 01 | Steps to reproduce:");
        int index2 = result.indexOf("Bug: ID -> [2] 'New Bug 1 For Testing' | Status: Fixed | Priority: Medium | Severity: Minor | Assignee: User 01 | Steps to reproduce:");
        Assertions.assertNotEquals(-1,index1);
        Assertions.assertNotEquals(-1,index2);
    }

    @Test
    void execute_Should_ReturnFilteredByStatusTasks() {
        // Arrange
        List<String> parameters = new ArrayList<String>();
        parameters.add("FilterByStatus");
        parameters.add("Active");

        // Act
        String result = command.execute(parameters);
        // Assert
        int index1 = result.indexOf("Bug: ID -> [1] 'New Bug 2 For Testing' | Status: Active | Priority: Low | Severity: Minor | Assignee: User 01 | Steps to reproduce:");
        int index2 = result.indexOf("Bug: ID -> [2] 'New Bug 1 For Testing' | Status: Fixed | Priority: Medium | Severity: Minor | Assignee: User 01 | Steps to reproduce:");
        Assertions.assertNotEquals(-1,index1);
        Assertions.assertEquals(-1,index2);
    }

    @Test
    void execute_Should_ReturnSortedByStatusTasks() {
        // Arrange
        List<String> parameters = new ArrayList<String>();
        parameters.add("SortByTitle");

        // Act
        String result = command.execute(parameters);
        // Assert
        int index1 = result.indexOf("Bug: ID -> [1] 'New Bug 2 For Testing' | Status: Active | Priority: Low | Severity: Minor | Assignee: User 01 | Steps to reproduce:");
        int index2 = result.indexOf("Bug: ID -> [2] 'New Bug 1 For Testing' | Status: Fixed | Priority: Medium | Severity: Minor | Assignee: User 01 | Steps to reproduce:");
        boolean ordered = index1 - index2>0;
        Assertions.assertEquals(true,ordered);
    }

    @Test
    void execute_Should_ReturnFilteredByAssigneeAndSortedTasks() {
        // Arrange
        List<String> parameters = new ArrayList<String>();
        parameters.add("FilterByAssignee");
        parameters.add("User 01");
        parameters.add("SortByTitle");
        // Act
        String result = command.execute(parameters);
        // Assert
        int index1 = result.indexOf("Bug: ID -> [1] 'New Bug 2 For Testing' | Status: Active | Priority: Low | Severity: Minor | Assignee: User 01 | Steps to reproduce:");
        int index2 = result.indexOf("Bug: ID -> [2] 'New Bug 1 For Testing' | Status: Fixed | Priority: Medium | Severity: Minor | Assignee: User 01 | Steps to reproduce:");
        boolean ordered = index1 - index2>0;
        Assertions.assertNotEquals(-1,index1);
        Assertions.assertNotEquals(-1,index2);
        Assertions.assertEquals(true,ordered);
    }
    @Test
    void execute_Should_ReturnListNotSortedNotFiltered() {
        // Arrange
        List<String> parameters = new ArrayList<String>();
        // Act
        String result = command.execute(parameters);
        // Assert
        int index1 = result.indexOf("Bug: ID -> [1] 'New Bug 2 For Testing' | Status: Active | Priority: Low | Severity: Minor | Assignee: User 01 | Steps to reproduce:");
        int index2 = result.indexOf("Bug: ID -> [2] 'New Bug 1 For Testing' | Status: Fixed | Priority: Medium | Severity: Minor | Assignee: User 01 | Steps to reproduce:");
        int index3 = result.indexOf("Bug: ID -> [3] 'New Bug 3 For Testing' | Status: Active | Priority: Medium | Severity: Minor | Assignee: User 02 | Steps to reproduce:");
        Assertions.assertNotEquals(-1,index1);
        Assertions.assertNotEquals(-1,index2);
        Assertions.assertNotEquals(-1,index3);
    }
    @Test
    void execute_Should_ThrowException_When_ThereIsNoUserName() {
        // Arrange
        List<String> parameters = new ArrayList<String>();
        parameters.add("FilterByAssignee");
        parameters.add("");

        // Act
        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }
    @Test
    void execute_Should_ThrowException_When_ThereIsUnknownParameter() {
        // Arrange
        List<String> parameters = new ArrayList<String>();
        parameters.add("FilterBySomething");
        parameters.add("");

        // Act
        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }
    @Test
    void execute_Should_ThrowException_When_ThereIsInvalidStatus() {
        // Arrange
        List<String> parameters = new ArrayList<String>();
        parameters.add("FilterByStatus");
        parameters.add("InvalidStatus");

        // Act
        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }
}