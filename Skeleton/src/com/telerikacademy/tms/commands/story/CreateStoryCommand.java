package com.telerikacademy.tms.commands.story;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;

import java.util.List;

public class CreateStoryCommand implements Command {
    public CreateStoryCommand(TaskManagementRepository repository) {
    }

    @Override
    public String execute(List<String> parameters) {
        return null;
    }
}
