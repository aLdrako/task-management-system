package com.telerikacademy.tms.commands.Task;

import com.telerikacademy.tms.commands.Contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;

public class ListAllTasksCommand implements Command {
    public ListAllTasksCommand(TaskManagementRepository repository) {
    }
}
