package com.telerikacademy.tms.commands.person;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;

import java.util.List;

public class CreatePersonCommand implements Command {
    public CreatePersonCommand(TaskManagementRepository repository) {

    }

    @Override
    public String execute(List<String> parameters) {
        return null;
    }
}
