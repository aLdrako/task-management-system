package com.telerikacademy.tms.commands.task;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;

import java.util.List;

public class UnassignTaskFromPersonCommand implements Command {
    public UnassignTaskFromPersonCommand(TaskManagementRepository repository) {
    }

    @Override
    public String execute(List<String> parameters) {
        return null;
    }
}
