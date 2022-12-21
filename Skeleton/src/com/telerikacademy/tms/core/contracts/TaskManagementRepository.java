package com.telerikacademy.tms.core.contracts;

import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Bug;
import com.telerikacademy.tms.models.tasks.contracts.Feedback;
import com.telerikacademy.tms.models.tasks.contracts.Story;
import com.telerikacademy.tms.models.tasks.contracts.Task;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;
import com.telerikacademy.tms.models.tasks.enums.SizeType;

import java.util.List;

public interface TaskManagementRepository {
	List<Team> getTeams();
	List<User> getUsers();
	List<Board> getBoards();
	List<Task> getTasks();
	Team createTeam(String name);
	User createUser(String name);
	Board createBoard(String name);
	Bug createBug(String title, String description, PriorityType priority, SeverityType severity);
	Story createStory(String title, String description, PriorityType priority, SizeType size);
	Feedback createFeedback(String title, String description, int rating);
	<T extends Task> T findElementById(List<T> elements, int id);
}
