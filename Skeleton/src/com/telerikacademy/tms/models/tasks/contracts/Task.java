package com.telerikacademy.tms.models.tasks.contracts;

public interface Task extends Historiable, Commentable {
	int getID();

	String getTitle();

	String getDescription();

	Status getStatus();

	<T extends Status> void setStatus(T status);
}
