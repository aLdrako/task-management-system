package com.telerikacademy.tms.models.tasks;

import com.telerikacademy.tms.models.UserImpl;
import com.telerikacademy.tms.models.tasks.contracts.Bug;
import com.telerikacademy.tms.models.tasks.contracts.Story;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;
import com.telerikacademy.tms.models.tasks.enums.SizeType;
import org.junit.jupiter.api.Test;

import static com.telerikacademy.tms.utils.ModelsConstants.*;
import static com.telerikacademy.tms.utils.TestUtils.getList;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BugImplTests {

    @Test
    public void constructor_Should_ImplementBugInterface() {
        // Arrange, Act
        Bug bug = initializeBug(1);

        // Assert
        assertTrue(bug instanceof Bug);
    }

    @Test
    public void constructor_Should_ThrowException_When_NameLengthOutOfBounds() {
        // Arrange, Act, Assert
        assertThrows(IllegalArgumentException.class, () -> new BugImpl(1, TASK_INVALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SeverityType.MINOR, getList(0)));
    }

    @Test
    public void constructor_Should_ThrowException_When_DescriptionLengthOutOfBounds() {
        // Arrange, Act, Assert
        assertThrows(IllegalArgumentException.class, () -> new BugImpl(1, TASK_VALID_NAME, DESCRIPTION_INVALID_NAME, PriorityType.LOW, SeverityType.MINOR, getList(0)));
    }

    @Test
    public void constructor_Should_CreateNewBug_When_ValidArgumentsPassed() {
        // Arrange, Act
        Bug bug = initializeBug(1);

        // Assert
        assertAll(
                () -> assertEquals(TASK_VALID_NAME, bug.getTitle()),
                () -> assertEquals(DESCRIPTION_VALID_NAME, bug.getDescription()),
                () -> assertEquals(getList(1), bug.getSteps()),
                () -> assertEquals(PriorityType.LOW, bug.getPriority()),
                () -> assertEquals(SeverityType.MINOR, bug.getSeverity())
        );
    }

    @Test
    public void setAssignee_Should_AssignUserToBug() {
        // Arrange
        Bug bug = initializeBug(1);

        // Act
        bug.setAssignee(new UserImpl(USER_VALID_NAME));

        // Assert
        assertEquals(USER_VALID_NAME, bug.getAssignee().getName());
    }


    public static Bug initializeBug(int id) {
        return new BugImpl(id, TASK_VALID_NAME, DESCRIPTION_VALID_NAME, PriorityType.LOW, SeverityType.MINOR, getList(1));
    }

}
