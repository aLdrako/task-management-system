package com.telerikacademy.tms.commands.feedback;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;

import java.util.List;

public class ChangeFeedbackRatingCommand implements Command {
    public ChangeFeedbackRatingCommand(TaskManagementRepository repository) {
    }

    @Override
    public String execute(List<String> parameters) {
        return null;
    }
}
