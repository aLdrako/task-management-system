package com.telerikacademy.tms.models;

import com.telerikacademy.tms.models.compositions.HistoryImpl;
import com.telerikacademy.tms.models.compositions.contracts.History;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Nameable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.telerikacademy.tms.utils.ValidationHelpers.validateInRange;
import static java.lang.String.format;

public class TeamImpl implements Team {

	private static final int TEAM_MIN_LEN = 5;
	private static final int TEAM_MAX_LEN = 15;

	private static final String TEAM_LEN_ERR = format(
			"Team name must be between %s and %s symbols.",
			TEAM_MIN_LEN,
			TEAM_MAX_LEN);
	public static final String NEW_INSTANCE_MESSAGE = "Team was created.";

	private String name;
	private final List<User> users;
	private final List<Board> boards;
	private final List<History> activityHistory;

	public TeamImpl(String name) {
		setName(name);
		this.users = new ArrayList<>();
		this.boards = new ArrayList<>();
		this.activityHistory = new ArrayList<>();
		this.activityHistory.add(new HistoryImpl(NEW_INSTANCE_MESSAGE));
	}

	@Override
	public String getName() {
		return name;
	}

	private void setName(String name) {
		validateInRange(name.length(), TEAM_MIN_LEN, TEAM_MAX_LEN, TEAM_LEN_ERR);
		this.name = name;
	}

	@Override
	public List<User> getUsers() {
		return new ArrayList<>(users);
	}

	@Override
	public List<Board> getBoards() {
		return new ArrayList<>(boards);
	}

	@Override
	public void addUser(User user) {
		for (User u : getUsers()) {
			if (u.getName().equals(user.getName())) {
				throw new IllegalArgumentException("User already in list");
			}
		}
		this.users.add(user);
		this.activityHistory.add(new HistoryImpl(format("User %s added to the team %s", user.getName(), this.getName())));
	}

	@Override
	public void removeUser(User user) {
		for (User u : getUsers()) {
			if (u.getName().equals(user.getName())) {
				this.users.remove(user);
				this.activityHistory.add(new HistoryImpl(format("User %s removed from the team %s", user.getName(), this.getName())));
				return;
			}
		}
		throw new IllegalArgumentException("No such user in list");
	}

	@Override
	public void addBoard(Board board) {
		for (Board b : getBoards()) {
			if (b.getName().equals(board.getName())) {
				throw new IllegalArgumentException("Board already in list");
			}
		}
		this.boards.add(board);
		this.activityHistory.add(new HistoryImpl(format("Board %s added to the team %s", board.getName(), this.getName())));
	}

	@Override
	public void removeBoard(Board board) {
		for (Board b : getBoards()) {
			if (b.getName().equals(board.getName())) {
				this.boards.remove(board);
				this.activityHistory.add(new HistoryImpl(format("Board %s removed from the team %s", board.getName(), this.getName())));
				return;
			}
		}
		throw new IllegalArgumentException("No such board in list");
	}

	@Override
	public List<History> getHistories() {
		return new ArrayList<>(activityHistory);
	}

	@Override
	public String toString() {

		return this.getClass().getInterfaces()[0].getSimpleName() +
				": " + this.getName() + System.lineSeparator() +
				"Team has (" + this.getUsers().size() + ") users -> " +
				this.getUsers().stream().map(Nameable::getName).collect(Collectors.joining(", ")) +
				System.lineSeparator() +
				"Team contains (" + this.getBoards().size() + ") boards -> " +
				this.getBoards().stream().map(Nameable::getName).collect(Collectors.joining(", "));
	}
}
