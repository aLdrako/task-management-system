package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.tasks.contracts.Task;

import java.util.List;

import static com.telerikacademy.tms.utils.ListingHelpers.activityListing;
import static com.telerikacademy.tms.utils.ParsingHelpers.tryParseInt;
import static com.telerikacademy.tms.utils.ValidationHelpers.validateArgumentsCount;
import static java.lang.String.format;

public class ShowTaskActivity implements Command {
	private static final int EXPECTED_NUMBER_PARAMETERS = 1;
	private static final String CHANGES_HISTORY = "\n=== CHANGES HISTORY ===\n";
	private static final String COMMENTS = "\n=== COMMENTS ===\n";
	private static final String NO_COMMENTS = "\n=== NO COMMENTS ===";
	private static final String TASK_ACTIVITY = "<<< %s ACTIVITY with ID -> [%d] >>>";

	private final TaskManagementRepository repository;


	public ShowTaskActivity(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		int id = tryParseInt(parameters.get(0));
		return showTaskActivity(id);
	}

	private String showTaskActivity(int id) {
		Task task = repository.findTaskById(repository.getTasks(), id);

		String anyComments = task.getComments().size() == 0 ? NO_COMMENTS : COMMENTS;

		return format(TASK_ACTIVITY, task.getTaskType(), task.getID()) +
				CHANGES_HISTORY + activityListing(task.getHistories()) +
				anyComments + activityListing(task.getComments());
	}
}
