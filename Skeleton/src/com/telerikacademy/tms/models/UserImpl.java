package com.telerikacademy.tms.models;

import com.telerikacademy.tms.models.compositions.HistoryImpl;
import com.telerikacademy.tms.models.compositions.contracts.History;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Task;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.tms.utils.ValidationHelpers.*;
import static java.lang.String.format;

public class UserImpl implements User {

	private static final int USER_MIN_LEN = 5;
	private static final int USER_MAX_LEN = 15;

	private static final String USER_LEN_ERR = format(
			"User name must be between %s and %s symbols.",
			USER_MIN_LEN,
			USER_MAX_LEN);

	private String name;
	private final List<Task> tasks;
	private final List<History> histories;

	public UserImpl(String name) {
		setName(name);
		this.tasks = new ArrayList<>();
		this.histories = new ArrayList<>();
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
		this.populateHistory(new HistoryImpl(format("Task %s assigned to %s", task.getTitle(), this.getName())));
	}

	@Override
	public void unAssignTask(Task task) {
		for (Task t : getTasks()) {
			if (t.getID() == task.getID()) {
				this.tasks.remove(task);
				this.populateHistory(new HistoryImpl(format("Task %s unassigned from %s", task.getTitle(), this.getName())));
				return;
			}
		}
		throw new IllegalArgumentException("No such task assigned");
	}

	@Override
	public List<History> getHistories() {
		return new ArrayList<>(histories);
	}

	@Override
	public void populateHistory(History history) {
		this.histories.add(history);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append(this.getClass().getInterfaces()[0].getSimpleName())
				.append(": ").append(this.getName()).append(System.lineSeparator());
		result.append("List of assigned Tasks").append(System.lineSeparator());
		for (Task task : getTasks()) {
			result.append(task.getID()).append(" ").append(task.getTitle()).append(" | Status: ").append(task.getStatus())
					.append(System.lineSeparator());
		}
		result.append("Activity History").append(System.lineSeparator());
		for (History history : getHistories()) {
			result.append(history.getHistory()).append(System.lineSeparator());
		}

		return result.toString();
	}
}
