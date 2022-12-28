package com.telerikacademy.tms.models;

import com.telerikacademy.tms.models.compositions.HistoryImpl;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Task;
import org.junit.jupiter.api.Test;

import static com.telerikacademy.tms.models.BoardImplTests.initializeTask;
import static com.telerikacademy.tms.utils.ModelsConstants.USER_INVALID_NAME;
import static com.telerikacademy.tms.utils.ModelsConstants.USER_VALID_NAME;
import static org.junit.jupiter.api.Assertions.*;

public class UserImplTest {

	@Test
	public void userImpl_Should_ImplementUserInterface() {
		// Arrange, Act
		User user = new UserImpl(USER_VALID_NAME);

		// Assert
		assertTrue(user instanceof User);
	}

	@Test
	public void constructor_Should_ThrowException_When_NameLengthOutOfBounds() {
		// Arrange, Act, Assert
		assertThrows(IllegalArgumentException.class, () -> new UserImpl(USER_INVALID_NAME));
	}

	@Test
	public void constructor_Should_CreateNewUser_When_ValidArgumentsPassed() {
		// Arrange, Act
		User user = new UserImpl(USER_VALID_NAME);

		// Assert
		assertEquals(USER_VALID_NAME, user.getName());
	}

	@Test
	public void getTasks_Should_ReturnCopyOfListTasks() {
		// Arrange
		User user = new UserImpl(USER_VALID_NAME);
		Task task = initializeTask(1);
		user.assignTask(task);

		// Act
		user.getTasks().add(task);

		// Assert
		assertEquals(1, user.getTasks().size());
	}

	@Test
	public void assignTask_Should_AddTaskToList() {
		// Arrange
		User user = new UserImpl(USER_VALID_NAME);
		Task task = initializeTask(1);

		// Act
		user.assignTask(task);

		// Assert
		assertEquals(1, user.getTasks().size());
	}

	@Test
	public void assignTask_Should_ThrowException_When_AddingSameTask() {
		// Arrange
		User user = new UserImpl(USER_VALID_NAME);
		Task task = initializeTask(1);
		user.assignTask(task);

		// Act, Assert
		assertThrows(IllegalArgumentException.class, () -> user.assignTask(task));
	}

	@Test
	public void unAssignTask_Should_RemoveTaskFromList() {
		// Arrange
		User user = new UserImpl(USER_VALID_NAME);
		Task task = initializeTask(1);
		Task task2 = initializeTask(2);

		// Act
		user.assignTask(task);
		user.assignTask(task2);
		user.unAssignTask(task);

		// Assert
		assertEquals(1, user.getTasks().size());
	}

	@Test
	public void unAssignTask_Should_ThrowException_When_RemovingNonExistedTaskInList() {
		// Arrange
		User user = new UserImpl(USER_VALID_NAME);
		Task task = initializeTask(1);
		Task task2 = initializeTask(2);
		user.assignTask(task);

		// Act, Assert
		assertThrows(IllegalArgumentException.class, () -> user.unAssignTask(task2));
	}

	@Test
	public void addCommentActivity_Should_HistoryActivityToList() {
		// Arrange
		User user = new UserImpl(USER_VALID_NAME);
		Task task = initializeTask(1);

		// Act
		user.addCommentActivity(task);

		// Assert
		assertEquals(2, user.getHistories().size());
	}

	@Test
	public void getHistories_Should_ReturnCopyOfListTasks() {
		// Arrange
		User user = new UserImpl(USER_VALID_NAME);

		// Act
		user.getHistories().add(new HistoryImpl("History"));

		// Assert
		assertEquals(1, user.getHistories().size());
	}

}
