package com.telerikacademy.tms.models.contracts;

import com.telerikacademy.tms.models.tasks.contracts.Historiable;
import com.telerikacademy.tms.models.tasks.contracts.Nameable;

import java.util.List;

public interface Team extends Nameable, Historiable {
    List<User> getUsers();

    List<Board> getBoards();

    void addUser(User user);

    void addBoard(Board board);
}
