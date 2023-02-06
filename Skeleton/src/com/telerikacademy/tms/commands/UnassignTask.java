package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.UserImpl;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Assignable;

import java.util.List;

import static com.telerikacademy.tms.utils.ParsingHelpers.tryParseInt;
import static com.telerikacademy.tms.utils.ValidationHelpers.validateArgumentsCount;
import static java.lang.String.format;

public class UnassignTask implements Command {
    private static final String TASK_UNASSIGNED_SUCCESSFUL = "Task with ID -> [%s] was unassigned from user <%s>.";
    private static final String TASK_ALREADY_UNASSIGNED = "Task with ID -> [%s] is already unassigned.";

    private static final int EXPECTED_NUMBER_PARAMETERS = 1;
    private final TaskManagementRepository repository;

    public UnassignTask(TaskManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
        int id = tryParseInt(parameters.get(0));

        return unassignTask(id);
    }

    private String unassignTask(int id) {
        Assignable assignableTask = repository.findTaskById(repository.getAssignableTasks(), id, "Assignable task");
        if (assignableTask.getAssignee().getName().equalsIgnoreCase("Unassigned")) {
            throw new InvalidUserInputException(format(TASK_ALREADY_UNASSIGNED, assignableTask.getID()));
        }
        User previousAssignee = assignableTask.getAssignee();
        previousAssignee.unAssignTask(assignableTask);
        assignableTask.setAssignee(new UserImpl("Unassigned"));


        return format(TASK_UNASSIGNED_SUCCESSFUL, assignableTask.getID(), previousAssignee.getName());
    }
}
