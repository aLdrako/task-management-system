package com.telerikacademy.tms.commands.team;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;

import java.util.List;

public class ShowAllTeamsCommand implements Command {
    public ShowAllTeamsCommand(TaskManagementRepository repository) {
    }

    @Override
    public String execute(List<String> parameters) {
        return null;
    }
}
