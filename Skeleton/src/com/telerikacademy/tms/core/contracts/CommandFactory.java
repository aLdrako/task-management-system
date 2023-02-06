package com.telerikacademy.tms.core.contracts;

import com.telerikacademy.tms.commands.contracts.Command;

public interface CommandFactory {
    Command createCommandFromCommandName(String commandName, TaskManagementRepository repository);
}
