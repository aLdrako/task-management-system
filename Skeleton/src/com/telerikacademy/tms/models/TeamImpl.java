package com.telerikacademy.tms.models;

import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.contracts.User;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.tms.utils.ValidationHelpers.*;
import static java.lang.String.format;

public class TeamImpl implements Team {

	private static final int TEAM_MIN_LEN = 5;
	private static final int TEAM_MAX_LEN = 15;

	private static final String TEAM_LEN_ERR = format(
			"Team name must be between %s and %s symbols.",
			TEAM_MIN_LEN,
			TEAM_MAX_LEN);

	private String name;

	private final List<User> users;
	private final List<Board> boards;

	public TeamImpl(String name) {
		setName(name);
		this.users = new ArrayList<>();
		this.boards = new ArrayList<>();
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
	}

	@Override
	public void removeUser(User user) {
		for (User u : getUsers()) {
			if (u.getName().equals(user.getName())) {
				this.users.remove(user);
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
	}

	@Override
	public void removeBoard(Board board) {
		for (Board b : getBoards()) {
			if (b.getName().equals(board.getName())) {
				this.boards.remove(board);
				return;
			}
		}
		throw new IllegalArgumentException("No such board in list");
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append(this.getClass().getInterfaces()[0].getSimpleName())
				.append(": ").append(this.getName()).append(System.lineSeparator());
		result.append("List of Users: ");
		for (User user : getUsers()) {
			result.append(user.getName()).append(", ");
		}
		result.append(System.lineSeparator()).append("List of Boards: ");
		for (Board board : getBoards()) {
			result.append(board.getName()).append(", ");
		}

		return result.toString();
	}
}
