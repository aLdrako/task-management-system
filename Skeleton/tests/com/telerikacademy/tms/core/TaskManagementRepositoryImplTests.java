package com.telerikacademy.tms.core;

import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.ElementNotFoundException;
import com.telerikacademy.tms.models.UserImpl;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.*;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.Rating;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;
import com.telerikacademy.tms.models.tasks.enums.SizeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.util.List;

import static com.telerikacademy.tms.utils.ModelsConstants.*;
import static com.telerikacademy.tms.utils.TestUtils.getList;

public class TaskManagementRepositoryImplTests {

    private TaskManagementRepository repository;

    @BeforeEach
    public void before() {
        repository = new TaskManagementRepositoryImpl();
    }

    @Test
    public void constructor_Should_InitializeAllCollections() {
        // Arrange, Act, Assert
        Assertions.assertAll(
                () -> Assertions.assertNotNull(repository.getTeams()),
                () -> Assertions.assertNotNull(repository.getUsers()),
                () -> Assertions.assertNotNull(repository.getTasks()),
                () -> Assertions.assertNotNull(repository.getBugs()),
                () -> Assertions.assertNotNull(repository.getFeedbacks()),
                () -> Assertions.assertNotNull(repository.getStories()),
                () -> Assertions.assertNotNull(repository.getAssignableTasks()),
                () -> Assertions.assertNotNull(repository.getBoards())
        );
    }

    @Test
    public void getTeam_Should_ReturnCopyOfCollection() {
        // Arrange
        List<Team> teamsFirst = repository.getTeams();
        List<Team> teamsSecond = repository.getTeams();

        // Act, Assert
        Assertions.assertNotSame(teamsFirst, teamsSecond);
    }
    @Test
    public void getUsers_Should_ReturnCopyOfCollection() {
        // Arrange
        List<User> usersFirst = repository.getUsers();
        List<User> usersSecond = repository.getUsers();

        // Act, Assert
        Assertions.assertNotSame(usersFirst, usersSecond);
    }
    @Test
    public void getTasks_Should_ReturnCopyOfCollection() {
        // Arrange
        List<Task> tasksFirst = repository.getTasks();
        List<Task> tasksSecond = repository.getTasks();

        // Act, Assert
        Assertions.assertNotSame(tasksFirst, tasksSecond);
    }

    @Test
    public void getBugs_Should_ReturnCopyOfCollection() {
        // Arrange
        List<Bug> bugsFirst = repository.getBugs();
        List<Bug> bugsSecond = repository.getBugs();

        // Act, Assert
        Assertions.assertNotSame(bugsFirst, bugsSecond);
    }
    @Test
    public void getFeedbacks_Should_ReturnCopyOfCollection() {
        // Arrange
        List<Feedback> feedbacksFirst = repository.getFeedbacks();
        List<Feedback> feedbacksSecond = repository.getFeedbacks();

        // Act, Assert
        Assertions.assertNotSame(feedbacksFirst, feedbacksSecond);
    }
    @Test
    public void getStories_Should_ReturnCopyOfCollection() {
        // Arrange
        List<Story> storiesFirst = repository.getStories();
        List<Story> storiesSecond = repository.getStories();

        // Act, Assert
        Assertions.assertNotSame(storiesFirst, storiesSecond);
    }
    @Test
    public void getAssignableTasks_Should_ReturnCopyOfCollection() {
        // Arrange
        List<Assignable> assignablesFirst = repository.getAssignableTasks();
        List<Assignable> assignablesSecond = repository.getAssignableTasks();

        // Act, Assert
        Assertions.assertNotSame(assignablesFirst, assignablesSecond);
    }
    @Test
    public void getBoards_Should_ReturnCopyOfCollection() {
        // Arrange
        List<Board> boardsFirst = repository.getBoards();
        List<Board> boardsSecond = repository.getBoards();

        // Act, Assert
        Assertions.assertNotSame(boardsFirst, boardsSecond);
    }
    @Test
    public void createTeam_Should_AddTeamToList() {
        // Arrange
        repository.createTeam(TEAM_VALID_NAME);

        // Act, Assert
        Assertions.assertEquals(1, repository.getTeams().size());
    }
    @Test
    public void createUser_Should_AddUserToList() {
        // Arrange
        repository.createUser(USER_VALID_NAME);

        // Act, Assert
        Assertions.assertEquals(1, repository.getUsers().size());
    }
    @Test
    public void createBoard_Should_AddBoardToList() {
        // Arrange
        repository.createBoard(BOARD_VALID_NAME);

        // Act, Assert
        Assertions.assertEquals(1, repository.getBoards().size());
    }
    @Test
    public void createBug_Should_AddBugToLists() {
        // Arrange
        repository.createBug(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SeverityType.MINOR,
                getList(1));

        // Act, Assert
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, repository.getBugs().size()),
                () -> Assertions.assertEquals(1, repository.getTasks().size()),
                () -> Assertions.assertEquals(1, repository.getAssignableTasks().size())
        );
    }
    @Test
    public void createStory_Should_AddStoryToLists() {
        // Arrange
        repository.createStory(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SizeType.LARGE);

        // Act, Assert
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, repository.getStories().size()),
                () -> Assertions.assertEquals(1, repository.getTasks().size()),
                () -> Assertions.assertEquals(1, repository.getAssignableTasks().size())
        );
    }
    @Test
    public void createFeedback_Should_AddFeedbackToLists() {
        // Arrange
        repository.createFeedback(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, Rating.FIVE);

        // Act, Assert
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, repository.getFeedbacks().size()),
                () -> Assertions.assertEquals(1, repository.getTasks().size())
        );
    }
    @Test
    public void findTaskById_Should_ReturnTask() {
        // Arrange
        Task task = repository.createStory(TASK_VALID_NAME, DESCRIPTION_VALID_NAME,
                PriorityType.LOW, SizeType.LARGE);
        // Act
        int idTask = task.getID();

        // Assert

        Assertions.assertAll(
                () -> Assertions.assertSame(task, repository.findTaskById(repository.getStories(), idTask)),
                () -> Assertions.assertSame(task, repository.findTaskById(repository.getAssignableTasks(), idTask)),
                () -> Assertions.assertSame(task, repository.findTaskById(repository.getTasks(), idTask))
        );
    }
    @Test
    public void findTaskById_Should_ThrowException_When_TaskIsNotFoundInList() {
        // Arrange, Act, Assert
        Assertions.assertThrows(ElementNotFoundException.class, () -> repository.findTaskById(repository.getTasks(), 1));
    }
    @Test
    public void findBoardByTask_Should_ReturnBoard() {
        // Arrange
        Board board = repository.createBoard(BOARD_VALID_NAME);
        Task task = repository.createStory(TASK_VALID_NAME, DESCRIPTION_VALID_NAME,
                PriorityType.LOW, SizeType.LARGE);
        // Act
        board.addTask(task);

        // Assert
        Assertions.assertSame(board, repository.findBoardByTask(task));
    }
    @Test
    public void findBoardByTask_Should_ThrowException_When_BoardIsNotFound() {
        // Arrange
        Task task = repository.createFeedback(TASK_VALID_NAME, DESCRIPTION_VALID_NAME, Rating.FIVE);
        // Act, Assert
        Assertions.assertThrows(ElementNotFoundException.class, () -> repository.findBoardByTask(task));
    }
    @Test
    public void findTeamByBoard_Should_ReturnTeam() {
        // Arrange
        Team team = repository.createTeam(TEAM_VALID_NAME);
        Board board = repository.createBoard(BOARD_VALID_NAME);
        // Act
        team.addBoard(board);
        // Assert
        Assertions.assertSame(team, repository.findTeamByBoard(board));
    }
    @Test
    public void findTeamByBoard_Should_ThrowException_When_TeamIsNotFound() {
        // Arrange
        Board board = repository.createBoard(BOARD_VALID_NAME);
        // Act, Assert
        Assertions.assertThrows(ElementNotFoundException.class, () -> repository.findTeamByBoard(board));
    }
    @Test
    public void isNameUnique_Should_ReturnFalse_When_NameIsNotUnique() {
        // Arrange
        Team team = repository.createTeam(TEAM_VALID_NAME);
        User user = repository.createUser(USER_VALID_NAME);
        Board board = repository.createBoard(BOARD_VALID_NAME);
        // Act, Assert
        Assertions.assertAll(
                () -> Assertions.assertFalse(repository.isNameUnique(TEAM_VALID_NAME)),
                () -> Assertions.assertFalse(repository.isNameUnique(USER_VALID_NAME)),
                () -> Assertions.assertFalse(repository.isNameUnique(BOARD_VALID_NAME))
        );
    }
    @Test
    public void isNameUnique_Should_ReturnTrue_When_NameIsUnique() {
        // Arrange, Act, Assert
        Assertions.assertTrue(repository.isNameUnique(TEAM_VALID_NAME));
    }

    @Test
    public void findElementByName_Should_ReturnElement_When_ElementIsFound() {
        // Arrange
        Team team = repository.createTeam(TEAM_VALID_NAME);
        User user = repository.createUser(USER_VALID_NAME);
        Board board = repository.createBoard(BOARD_VALID_NAME);
        // Act, Assert
        Assertions.assertAll(
                () -> Assertions.assertSame(team, repository.findElementByName(repository.getTeams(), team.getName())),
                () -> Assertions.assertSame(user, repository.findElementByName(repository.getUsers(), user.getName())),
                () -> Assertions.assertSame(board, repository.findElementByName(repository.getBoards(), board.getName()))
        );
    }
    @Test
    public void findElementByName_Should_ThrowException_When_ElementIsNotFound() {
        // Arrange, Act, Assert
        Assertions.assertThrows(ElementNotFoundException.class, () -> repository.findElementByName(repository.getTeams(), TEAM_VALID_NAME));
    }
    @Test
    public void findBoardByNameInTeam_Should_ReturnBoard_When_BoardIsFound() {
        // Arrange
        Board board = repository.createBoard(BOARD_VALID_NAME);
        Team team = repository.createTeam(TEAM_VALID_NAME);
        // Act
        team.addBoard(board);
        // Arrange
        Assertions.assertSame(board, repository.findBoardByNameInTeam(team, board.getName()));
    }
    @Test
    public void findBoardByNameInTeam_Should_ThrowException_When_BoardIsNotFound() {
        // Arrange
        Team team = repository.createTeam(TEAM_VALID_NAME);
        // Act, Assert
        Assertions.assertThrows(ElementNotFoundException.class, () -> repository.findBoardByNameInTeam(team, BOARD_VALID_NAME));
    }
}
