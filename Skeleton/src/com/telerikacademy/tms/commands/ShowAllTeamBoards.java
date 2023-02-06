package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.tasks.contracts.Nameable;
import com.telerikacademy.tms.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class ShowAllTeamBoards implements Command {
    public static final int EXPECTED_NUMBER_PARAMETERS = 1;
    public static final String BOARDS_LISTED = "%s has (%s) team boards -> ";
    public static final String NO_BOARDS_LISTED = "%s team has no boards.";
    private final TaskManagementRepository repository;

    public ShowAllTeamBoards(TaskManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
        String teamName = parameters.get(0);
        Team findTeam = repository.findElementByName(repository.getTeams(), teamName);
        List<Board> boards = findTeam.getBoards();

        return (boards.size() == 0 ? format(NO_BOARDS_LISTED, findTeam.getName()) :
                format(BOARDS_LISTED, findTeam.getName(), boards.size())) +
                boards.stream().map(Nameable::getName).collect(Collectors.joining(", "));
    }
}
