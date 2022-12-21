package com.telerikacademy.tms.models.contracts;

import com.telerikacademy.tms.models.tasks.contracts.Historiable;

import java.util.List;

public interface Team extends Historiable {

	String getName();

	List<User> getUsers();

	List<Board> getBoards();

	void addUser(User user);

	void removeUser(User user);

	void addBoard(Board board);

	void removeBoard(Board board);

}
