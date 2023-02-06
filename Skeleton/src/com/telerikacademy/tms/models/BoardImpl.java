package com.telerikacademy.tms.models;

import com.telerikacademy.tms.models.compositions.HistoryImpl;
import com.telerikacademy.tms.models.compositions.contracts.History;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.tasks.contracts.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.telerikacademy.tms.utils.ValidationHelpers.validateInRange;
import static java.lang.String.format;

public class BoardImpl implements Board {
    private static final int BOARD_MIN_LEN = 5;
    private static final int BOARD_MAX_LEN = 10;
    private static final String BOARD_LEN_ERR = format(
            "Board name must be between %s and %s symbols.",
            BOARD_MIN_LEN,
            BOARD_MAX_LEN);
    private static final String NEW_INSTANCE_MESSAGE = "Board was created.";
    private static final String TASK_ALREADY_IN_BOARD = "Task with ID -> [%s] already in board <%s> list";
    private static final String TASK_ADDED_SUCCESSFUL = "Task '%s' with ID -> [%s] was added to board <%s>";
    private static final String CONTAIN_TASKS_AMOUNT = ": %s contains (%s) tasks";

    private String name;
    private final List<Task> tasks;
    private final List<History> activityHistory;

    public BoardImpl(String name) {
        setName(name);
        this.tasks = new ArrayList<>();
        this.activityHistory = new ArrayList<>();
        this.activityHistory.add(new HistoryImpl(NEW_INSTANCE_MESSAGE));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public List<History> getHistories() {
        return new ArrayList<>(activityHistory);
    }

    private void setName(String name) {
        validateInRange(name.length(), BOARD_MIN_LEN, BOARD_MAX_LEN, BOARD_LEN_ERR);
        this.name = name;
    }

    @Override
    public void addTask(Task task) {
        if (getTasks().stream().anyMatch(task1 -> task1.getID() == task.getID())) {
            throw new IllegalArgumentException(format(TASK_ALREADY_IN_BOARD, task.getID(), this.getName()));
        }
        this.tasks.add(task);
        this.activityHistory.add(new HistoryImpl(format(TASK_ADDED_SUCCESSFUL, task.getTitle(), task.getID(), this.getName())));
    }

    @Override
    public String toString() {
        return this.getClass().getInterfaces()[0].getSimpleName() +
                format(CONTAIN_TASKS_AMOUNT, this.getName(), this.getTasks().size()) + System.lineSeparator() +
                this.getTasks().stream().map(Task::toString).collect(Collectors.joining("\n"));
    }
}
