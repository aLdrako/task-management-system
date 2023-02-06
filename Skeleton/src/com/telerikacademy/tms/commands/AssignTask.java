package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Assignable;

import java.util.List;

import static com.telerikacademy.tms.utils.ParsingHelpers.tryParseInt;
import static com.telerikacademy.tms.utils.ValidationHelpers.validateArgumentsCount;
import static java.lang.String.format;

public class AssignTask implements Command {
    private static final String TASK_ASSIGNED_SUCCESSFUL = "Task with ID -> [%s] was assigned to user <%s>.";
    private static final String USER_NOT_FOUND_IN_TEAM = "Cannot find user in team <%s> in order to assign task";
    private static final String TASK_ALREADY_ASSIGNED = "Task is already assigned to %s!";

    private static final int EXPECTED_NUMBER_PARAMETERS = 2;
    private final TaskManagementRepository repository;

    public AssignTask(TaskManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
        int id = tryParseInt(parameters.get(0));
        String userName = parameters.get(1);

        return assignTask(id, userName);
    }

    private String assignTask(int id, String userName) {
        Assignable assignableTask = repository.findTaskById(repository.getAssignableTasks(), id, "Assignable task");

        if (!assignableTask.getAssignee().getName().equalsIgnoreCase("Unassigned")) {
            throw new InvalidUserInputException(format(TASK_ALREADY_ASSIGNED, assignableTask.getAssignee().getName()));
        }
        User user = repository.findElementByName(repository.getUsers(), userName);
        Team team = repository.findTeamByTask(assignableTask);

        if (!team.getUsers().contains(user)) {
            throw new InvalidUserInputException(format(USER_NOT_FOUND_IN_TEAM, team.getName()));
        }
        user.assignTask(assignableTask);
        assignableTask.setAssignee(user);

        return format(TASK_ASSIGNED_SUCCESSFUL, assignableTask.getID(), user.getName());
    }
}
