package com.telerikacademy.tms.models.contracts;

import java.util.List;

public interface Team {

	String getName();

	List<User> getUsers();

	List<Board> getBoards();

	void addUser(User user);

	void removeUser(User user);

	void addBoard(Board board);

	void removeBoard(Board board);

}
