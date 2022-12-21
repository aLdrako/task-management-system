package com.telerikacademy.tms.models;

import com.telerikacademy.tms.models.compositions.HistoryImpl;
import com.telerikacademy.tms.models.compositions.contracts.History;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Task;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.tms.utils.ValidationHelpers.validateInRange;
import static java.lang.String.format;

public class UserImpl implements User {

	private static final int USER_MIN_LEN = 5;
	private static final int USER_MAX_LEN = 15;

	private static final String USER_LEN_ERR = format(
			"User name must be between %s and %s symbols.",
			USER_MIN_LEN,
			USER_MAX_LEN);
	public static final String NEW_INSTANCE_MESSAGE = "User was created.";

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
				throw new IllegalArgumentException("Task already assigned");
			}
		}
		this.tasks.add(task);
		this.activityHistory.add(new HistoryImpl(format("Task %s assigned to %s", task.getTitle(), this.getName())));
	}

	@Override
	public void unAssignTask(Task task) {
		for (Task t : getTasks()) {
			if (t.getID() == task.getID()) {
				this.tasks.remove(task);
				this.activityHistory.add(new HistoryImpl(format("Task %s unassigned from %s", task.getTitle(), this.getName())));
				return;
			}
		}
		throw new IllegalArgumentException("No such task assigned");
	}

	@Override
	public List<History> getHistories() {
		return new ArrayList<>(activityHistory);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append(this.getClass().getInterfaces()[0].getSimpleName())
				.append(": ").append(this.getName())
				.append(" has (").append(this.getTasks().size())
				.append(") assigned tasks").append(System.lineSeparator());

		for (Task task : this.getTasks()) {
			result.append("ID -> [").append(task.getID()).append("] ")
					.append(task.getTitle()).append(" | Status: ")
					.append(task.getStatus()).append(System.lineSeparator());
		}

		result.append("<<< Activity History >>>".toUpperCase()).append(System.lineSeparator());
		for (History history : getHistories()) {
			result.append(history).append(System.lineSeparator());
		}

		return result.toString();
	}
}
