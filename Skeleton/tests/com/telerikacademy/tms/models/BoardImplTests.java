package com.telerikacademy.tms.models;

import com.telerikacademy.tms.models.compositions.HistoryImpl;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.tasks.BugImpl;
import com.telerikacademy.tms.models.tasks.contracts.Task;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;
import org.junit.jupiter.api.Test;

import static com.telerikacademy.tms.utils.ModelsConstants.*;
import static com.telerikacademy.tms.utils.TestUtils.getList;
import static org.junit.jupiter.api.Assertions.*;

public class BoardImplTests {

	@Test
	public void boardImpl_Should_Implement_BoardInterface() {
		// Arrange, Act
		BoardImpl board = new BoardImpl(BOARD_VALID_NAME);

		// Assert
		assertTrue(board instanceof Board);
	}

	@Test
	public void constructor_Should_ThrowException_When_NameLengthOutOfBounds() {
		// Arrange, Act, Assert
		assertThrows(IllegalArgumentException.class, () -> new BoardImpl(BOARD_INVALID_NAME));
	}

	@Test
	public void constructor_Should_CreateNewBoard_When_ValidArgumentsPassed() {
		// Arrange
		BoardImpl board = new BoardImpl(BOARD_VALID_NAME);
		// Act, Assert
		assertEquals(BOARD_VALID_NAME, board.getName());
	}

	@Test
	public void getTasks_Should_ReturnCopyOfListTasks() {
		// Arrange
		BoardImpl board = new BoardImpl(BOARD_VALID_NAME);
		Task task = initializeTask(1);

		// Act
		board.getTasks().add(task);

		// Assert
		assertEquals(0, board.getTasks().size());
	}

	@Test
	public void addTask_Should_AddTaskToList() {
		// Arrange
		BoardImpl board = new BoardImpl(BOARD_VALID_NAME);
		Task task = initializeTask(1);
		Task task2 = initializeTask(2);

		// Act
		board.addTask(task);
		board.addTask(task2);

		// Assert
		assertEquals(2, board.getTasks().size());
	}

	@Test
	public void addTask_Should_ThrowException_When_AddingSameTask() {
		// Arrange
		BoardImpl board = new BoardImpl(BOARD_VALID_NAME);
		Task task = initializeTask(1);
		board.addTask(task);

		// Act, Assert
		assertThrows(IllegalArgumentException.class, () -> board.addTask(task));
	}

	@Test
	public void getHistories_Should_ReturnCopyOfListTasks() {
		// Arrange
		BoardImpl board = new BoardImpl(BOARD_VALID_NAME);

		// Act
		board.getHistories().add(new HistoryImpl("History"));

		// Assert
		assertEquals(1, board.getHistories().size());
	}

	public static Task initializeTask(int id) {
		return new BugImpl(id, TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SeverityType.MINOR, getList(0));
	}

}
