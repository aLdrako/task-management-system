package com.telerikacademy.tms.models.tasks.contracts;

import com.telerikacademy.tms.models.tasks.enums.TaskType;

public interface Task extends Historiable, Commentable, Comparable<Task> {
    int getID();

    String getTitle();

    String getDescription();

    Status getStatus();

    <T extends Status> void setStatus(T status);

    TaskType getTaskType();
}
