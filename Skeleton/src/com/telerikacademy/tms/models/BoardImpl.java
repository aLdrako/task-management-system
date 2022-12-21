package com.telerikacademy.tms.models;

import com.telerikacademy.tms.models.compositions.HistoryImpl;
import com.telerikacademy.tms.models.compositions.contracts.History;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.tasks.contracts.Task;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.tms.utils.ValidationHelpers.*;
import static java.lang.String.format;

public class BoardImpl implements Board {
	private static final int BOARD_MIN_LEN = 5;
	private static final int BOARD_MAX_LEN = 10;

	private static final String BOARD_LEN_ERR = format(
			"Board name must be between %s and %s symbols.",
			BOARD_MIN_LEN,
			BOARD_MAX_LEN);
	public static final String NEW_INSTANCE_MESSAGE = "Board was created.";

	private String name;

	private final List<Task> tasks;

	private final List<History> activityHistory;

	public BoardImpl(String name) {
		setName(name);
		this.tasks = new ArrayList<>();
		this.activityHistory = new ArrayList<>();
		this.activityHistory.add(new HistoryImpl(NEW_INSTANCE_MESSAGE));
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		validateInRange(name.length(), BOARD_MIN_LEN, BOARD_MAX_LEN, BOARD_LEN_ERR);
		this.name = name;
	}

	@Override
	public List<Task> getTasks() {
		return new ArrayList<>(tasks);
	}

	@Override
	public void addTask(Task task) {
		for (Task t : getTasks()) {
			if (t.getID() == task.getID()) {
				throw new IllegalArgumentException("Task already in list");
			}
		}
		this.tasks.add(task);
		this.activityHistory.add(new HistoryImpl(format("Task %s added to board %s", task.getTitle(), this.getName())));
	}

	@Override
	public void removeTask(Task task) {
		for (Task t : getTasks()) {
			if (t.getID() == task.getID()) {
				this.tasks.remove(task);
				this.activityHistory.add(new HistoryImpl(format("Task %s removed from board %s", task.getTitle(), this.getName())));
				return;
			}
		}
		throw new IllegalArgumentException("No such task in list");
	}

	@Override
	public List<History> getHistories() {
		return new ArrayList<>(activityHistory);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append(this.getClass().getInterfaces()[0].getSimpleName())
				.append(": ").append(this.getName()).append(System.lineSeparator());

		result.append("List of Tasks").append(System.lineSeparator());
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
