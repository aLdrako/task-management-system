package com.telerikacademy.tms.models;

import com.telerikacademy.tms.models.compositions.HistoryImpl;
import com.telerikacademy.tms.models.compositions.contracts.Comment;
import com.telerikacademy.tms.models.compositions.contracts.History;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.telerikacademy.tms.utils.ValidationHelpers.validateInRange;
import static java.lang.String.format;

public class UserImpl implements User {
	private static final int USER_MIN_LEN = 5;
	private static final int USER_MAX_LEN = 15;
	private static final String USER_LEN_ERR = format(
			"User name must be between %s and %s symbols.",
			USER_MIN_LEN,
			USER_MAX_LEN);
	private static final String NEW_INSTANCE_MESSAGE = "User was created.";
	private static final String TASK_ALREADY_ASSIGNED = "Task with ID %s already assigned to %s";
	private static final String TASK_ASSIGNED_SUCCESSFUL = "Task '%s' assigned to %s";
	private static final String TASK_UNASSIGNED_SUCCESSFUL = "Task '%s' unassigned from %s";
	private static final String TASK_NOT_ASSIGNED = "Task with ID %s in not assigned to %s";
	private static final String COMMENT_ADDED_TO_TASK_SUCCESSFUL = "Added comment to task with ID %s";

	private String name;
	private final List<Task> tasks;
	private final List<History> activityHistory;

	public UserImpl(String name) {
		setName(name);
		this.tasks = new ArrayList<>();
		this.activityHistory = new ArrayList<>();
		this.activityHistory.add(new HistoryImpl(NEW_INSTANCE_MESSAGE));
	}

	@Override
	public String getName() {
		return name;
	}

	private void setName(String name) {
		validateInRange(name.length(), USER_MIN_LEN, USER_MAX_LEN, USER_LEN_ERR);
		this.name = name;
	}

	@Override
	public List<Task> getTasks() {
		return new ArrayList<>(tasks);
	}

	@Override
	public void assignTask(Task task) {
		for (Task t : getTasks()) {
			if (t.getID() == task.getID()) {
				throw new IllegalArgumentException(format(TASK_ALREADY_ASSIGNED, task.getID(), this.getName()));
			}
		}
		this.tasks.add(task);
		this.activityHistory.add(new HistoryImpl(format(TASK_ASSIGNED_SUCCESSFUL, task.getTitle(), this.getName())));
	}

	@Override
	public void unAssignTask(Task task) {
		for (Task t : getTasks()) {
			if (t.getID() == task.getID()) {
				this.tasks.remove(task);
				this.activityHistory.add(new HistoryImpl(format(TASK_UNASSIGNED_SUCCESSFUL, task.getTitle(), this.getName())));
				return;
			}
		}
		throw new IllegalArgumentException(format(TASK_NOT_ASSIGNED, task.getID(), this.getName()));
	}

	@Override
	public void addCommentActivity(Task task) {
		this.activityHistory.add(new HistoryImpl(format(COMMENT_ADDED_TO_TASK_SUCCESSFUL, task.getID())));
	}

	@Override
	public List<History> getHistories() {
		return new ArrayList<>(activityHistory);
	}

	@Override
	public String toString() {

		return this.getClass().getInterfaces()[0].getSimpleName() +
				": " + this.getName() +
				" has (" + this.getTasks().size() +
				") assigned tasks" + System.lineSeparator() +
				this.getTasks().stream().map(Task::toString).collect(Collectors.joining("\n")) +
				System.lineSeparator() + "<<< Activity History >>>".toUpperCase() + System.lineSeparator() +
				this.getHistories().stream().map(History::toString).collect(Collectors.joining("\n"));
	}
}
