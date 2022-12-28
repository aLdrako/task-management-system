package com.telerikacademy.tms.models.contracts;

import com.telerikacademy.tms.models.tasks.contracts.Historiable;
import com.telerikacademy.tms.models.tasks.contracts.Nameable;
import com.telerikacademy.tms.models.tasks.contracts.Task;

import java.util.List;

public interface User extends Nameable, Historiable {
	List<Task> getTasks();

	void assignTask(Task task);

	void unAssignTask(Task task);

	void addCommentActivity(Task task);

	void populateHistoryWhenAddingToTeam(Team team);
}
