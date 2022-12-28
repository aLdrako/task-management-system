package com.telerikacademy.tms.models.tasks;

import com.telerikacademy.tms.models.UserImpl;
import com.telerikacademy.tms.models.tasks.contracts.Story;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SizeType;
import org.junit.jupiter.api.Test;

import static com.telerikacademy.tms.utils.ModelsConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class StoryImplTests {

	@Test
	public void constructor_Should_ImplementStoryInterface() {
		// Arrange, Act
		Story story = initializeStory(1);

		// Assert
		assertTrue(story instanceof Story);
	}

	@Test
	public void constructor_Should_ThrowException_When_NameLengthOutOfBounds() {
		// Arrange, Act, Assert
		assertThrows(IllegalArgumentException.class, () -> new StoryImpl(1, TASK_INVALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SizeType.SMALL));
	}

	@Test
	public void constructor_Should_ThrowException_When_DescriptionLengthOutOfBounds() {
		// Arrange, Act, Assert
		assertThrows(IllegalArgumentException.class, () -> new StoryImpl(1, TASK_VALID_NAME, DESCRIPTION_INVALID_NAME, PriorityType.LOW, SizeType.SMALL));
	}

	@Test
	public void constructor_Should_CreateNewStory_When_ValidArgumentsPassed() {
		// Arrange, Act
		Story story = initializeStory(1);

		// Assert
		assertAll(
				() -> assertEquals(TASK_VALID_NAME, story.getTitle()),
				() -> assertEquals(DESCRIPTION_VALID_NAME, story.getDescription()),
				() -> assertEquals(PriorityType.LOW, story.getPriority()),
				() -> assertEquals(SizeType.SMALL, story.getSize())
		);
	}

	@Test
	public void setAssignee_Should_AssignUserToStory() {
		// Arrange
		Story story = initializeStory(1);

		// Act
		story.setAssignee(new UserImpl(USER_VALID_NAME));

		// Assert
		assertEquals(USER_VALID_NAME, story.getAssignee().getName());
	}

	public static Story initializeStory(int id) {
		return new StoryImpl(id, TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SizeType.SMALL);
	}
}
