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
	private static final String NEW_INSTANCE_MESSAGE = "Team was created.";
	private static final String USER_ALREADY_IN_TEAM = "User <%s> already in team <%s>";
	private static final String USER_ADDED_SUCCESSFUL = "User <%s> was added to the team <%s>";
	private static final String BOARD_ALREADY_IN_TEAM = "Board <%s> already in team <%s>";
	private static final String BOARD_ADDED_SUCCESSFUL = "Board <%s> was added to the team <%s>";
	private static final String CONTAIN_USERS_AMOUNT = "It has (%s) users";
	private static final String CONTAIN_BOARDS_AMOUNT = "It contains (%s) boards";

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

	@Override
	public List<User> getUsers() {
		return new ArrayList<>(users);
	}

	@Override
	public List<Board> getBoards() {
		return new ArrayList<>(boards);
	}

	private void setName(String name) {
		validateInRange(name.length(), TEAM_MIN_LEN, TEAM_MAX_LEN, TEAM_LEN_ERR);
		this.name = name;
	}

	@Override
	public void addUser(User user) {
		if (getUsers().stream().anyMatch(user1 -> user1.getName().equalsIgnoreCase(user.getName()))) {
			throw new IllegalArgumentException(format(USER_ALREADY_IN_TEAM, user.getName(), this.getName()));
		}
		this.users.add(user);
		user.populateHistoryWhenAddingToTeam(this);
		this.activityHistory.add(new HistoryImpl(format(USER_ADDED_SUCCESSFUL, user.getName(), this.getName())));
	}

	@Override
	public void addBoard(Board board) {
		if (getBoards().stream().anyMatch(board1 -> board1.getName().equalsIgnoreCase(board.getName()))) {
			throw new IllegalArgumentException(format(BOARD_ALREADY_IN_TEAM, board.getName(), this.getName()));
		}
		this.boards.add(board);
		this.activityHistory.add(new HistoryImpl(format(BOARD_ADDED_SUCCESSFUL, board.getName(), this.getName())));
	}

	@Override
	public List<History> getHistories() {
		return new ArrayList<>(activityHistory);
	}

	@Override
	public String toString() {

		String usersExists = this.getUsers().size() != 0 ? " -> " : "";
		String boardsExists = this.getBoards().size() != 0 ? " -> " : "";
		return this.getClass().getInterfaces()[0].getSimpleName() + ": " + this.getName() + System.lineSeparator() +
				format(CONTAIN_USERS_AMOUNT, this.getUsers().size()) + usersExists +
				this.getUsers().stream().map(Nameable::getName).collect(Collectors.joining(", ")) +
				System.lineSeparator() +
				format(CONTAIN_BOARDS_AMOUNT, this.getBoards().size()) + boardsExists +
				this.getBoards().stream().map(Nameable::getName).collect(Collectors.joining(", "));
	}
}
