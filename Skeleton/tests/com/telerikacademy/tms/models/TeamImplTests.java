package com.telerikacademy.tms.models;

import com.telerikacademy.tms.models.compositions.HistoryImpl;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.contracts.User;
import org.junit.jupiter.api.Test;

import static com.telerikacademy.tms.utils.ModelsConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class TeamImplTests {

	@Test
	public void teamImpl_Should_Implement_TeamInterface() {
		// Arrange, Act
		Team team = new TeamImpl(TEAM_VALID_NAME);

		// Assert
		assertTrue(team instanceof Team);
	}

	@Test
	public void constructor_Should_ThrowException_When_NameLengthOutOfBounds() {
		// Arrange, Act, Assert
		assertThrows(IllegalArgumentException.class, () -> new TeamImpl(TEAM_INVALID_NAME));
	}

	@Test
	public void constructor_Should_CreateNewTeam_When_ValidArgumentsPassed() {
		// Arrange
		TeamImpl team = new TeamImpl(TEAM_VALID_NAME);
		// Act, Assert
		assertEquals(TEAM_VALID_NAME, team.getName());
	}

	@Test
	public void getUsers_Should_ReturnCopyOfListTasks() {
		// Arrange
		Team team = new TeamImpl(TEAM_VALID_NAME);
		User user = new UserImpl(USER_VALID_NAME);

		// Act
		team.getUsers().add(user);

		// Assert
		assertEquals(0, team.getUsers().size());
	}

	@Test
	public void getBoards_Should_ReturnCopyOfListTasks() {
		// Arrange
		Team team = new TeamImpl(TEAM_VALID_NAME);
		Board board = new BoardImpl(BOARD_VALID_NAME);

		// Act
		team.getBoards().add(board);

		// Assert
		assertEquals(0, team.getBoards().size());
	}

	@Test
	public void addUser_Should_AddUserToList() {
		// Arrange
		Team team = new TeamImpl(TEAM_VALID_NAME);
		User user = new UserImpl(USER_VALID_NAME);
		User user2 = new UserImpl(USER_VALID_NAME + " ");

		// Act
		team.addUser(user);
		team.addUser(user2);

		// Assert
		assertEquals(2, team.getUsers().size());
	}

	@Test
	public void addUser_Should_ThrowException_When_AddingSameUser() {
		// Arrange
		Team team = new TeamImpl(TEAM_VALID_NAME);
		User user = new UserImpl(USER_VALID_NAME);
		team.addUser(user);

		// Act, Assert
		assertThrows(IllegalArgumentException.class, () -> team.addUser(user));
	}

	@Test
	public void addBoard_Should_AddBoardToList() {
		// Arrange
		Team team = new TeamImpl(TEAM_VALID_NAME);
		Board board = new BoardImpl(BOARD_VALID_NAME);
		Board board2 = new BoardImpl(BOARD_VALID_NAME + " ");
		// Act
		team.addBoard(board);
		team.addBoard(board2);

		// Assert
		assertEquals(2, team.getBoards().size());
	}

	@Test
	public void addBoard_Should_ThrowException_When_AddingSameBoard() {
		// Arrange
		Team team = new TeamImpl(TEAM_VALID_NAME);
		Board board = new BoardImpl(BOARD_VALID_NAME);
		team.addBoard(board);

		// Act, Assert
		assertThrows(IllegalArgumentException.class, () -> team.addBoard(board));
	}

	@Test
	public void getHistories_Should_ReturnCopyOfListTasks() {
		// Arrange
		Team team = new TeamImpl(TEAM_VALID_NAME);

		// Act
		team.getHistories().add(new HistoryImpl("History"));

		// Assert
		assertEquals(1, team.getHistories().size());
	}
}
