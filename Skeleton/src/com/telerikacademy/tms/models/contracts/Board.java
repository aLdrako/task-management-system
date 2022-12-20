package com.telerikacademy.tms.models.contracts;

import com.telerikacademy.tms.models.tasks.contracts.Historiable;
import com.telerikacademy.tms.models.tasks.contracts.Task;

import java.util.List;

public interface Board extends Historiable {

	String getName();

	List<Task> getTasks();

	void addTask(Task task);

	void removeTask(Task task);
}
