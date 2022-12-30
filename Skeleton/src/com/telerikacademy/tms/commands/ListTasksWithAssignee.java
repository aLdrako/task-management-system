package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.tasks.contracts.Assignable;
import com.telerikacademy.tms.models.tasks.contracts.Task;
import com.telerikacademy.tms.models.tasks.enums.BugStatus;
import com.telerikacademy.tms.models.tasks.enums.StoryStatus;
import com.telerikacademy.tms.models.tasks.enums.TaskType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.telerikacademy.tms.utils.FilterHelpers.filterByStatus;
import static com.telerikacademy.tms.utils.ListingHelpers.elementsToString;
import static com.telerikacademy.tms.utils.ListingHelpers.listingCommandsSubHeader;
import static java.lang.String.format;

public class ListTasksWithAssignee implements Command {
	private static final String FILTER_BY_ASSIGNEE = "FilterByAssignee";
	private static final String FILTER_BY_STATUS = "FilterByStatus";
	private static final String SORT_BY_TITLE = "SortByTitle";
	private static final String NAME_CANNOT_BE_EMPTY = "Assignee name field cannot be empty";
	private static final String UNKNOWN_PARAMETER = "Unknown parameter \"%s\"";
	private static final String UNKNOWN_STATUS_PARAMETER = "Unknown status parameter \"%s\"";
	private static final String LISTING_HEADER = "LIST TASKS WITH ASSIGNEE %s %n%s";
	private final TaskManagementRepository repository;

	public ListTasksWithAssignee(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		boolean filterByStatus = false;
		boolean filterByAssignee = false;
		boolean sortByTitle = false;
		int i = 0;
		String assigneeName = "";
		String statusValue = "";
		List<Task> tasks = filterFeedbacksAndNotAssigned();
		//All tasks, not filtered, not sorted.
		if (parameters.size() == 0) {
			return format(LISTING_HEADER, listingCommandsSubHeader(parameters), elementsToString(tasks));
		}
		while (i < parameters.size()) {
			String temp = parameters.get(i);
			if (temp.equalsIgnoreCase(FILTER_BY_ASSIGNEE)) {
				filterByAssignee = true;
				assigneeName = parameters.get(i + 1);
				i += 2;
			} else if (temp.equalsIgnoreCase(FILTER_BY_STATUS)) {
				filterByStatus = true;
				statusValue = parameters.get(i + 1);
				i += 2;
			} else if (temp.equalsIgnoreCase(SORT_BY_TITLE)) {
				sortByTitle = true;
				i += 1;
			} else {
				throw new IllegalArgumentException(String.format(UNKNOWN_PARAMETER, temp));
			}
		}
		if (filterByAssignee) {
			tasks = filterByAssignee(tasks, assigneeName);
		}
		if (filterByStatus) {
			tasks = filterTasksByStatus(tasks, statusValue);
		}
		if (sortByTitle) {
			tasks = sortByTitle(tasks);
		}

		return format(LISTING_HEADER, listingCommandsSubHeader(parameters), elementsToString(tasks));
	}

	private List<Task> sortByTitle(List<Task> tasks) {
		Collections.sort(tasks);
		return tasks;
	}

	private List<Task> filterTasksByStatus(List<Task> tasks, String statusValue) {
		List<Task> result;
		try {
			result = filterByStatus(statusValue, tasks, BugStatus.class);
		} catch (Exception e) {
			try {
				result = filterByStatus(statusValue, tasks, StoryStatus.class);
			} catch (Exception a) {

				throw new IllegalArgumentException(String.format(UNKNOWN_STATUS_PARAMETER, statusValue));
			}
		}
		return result;
	}

	private List<Task> filterByAssignee(List<Task> tasks, String assigneeName) {
		if (assigneeName.isBlank() || assigneeName.isEmpty()) {
			throw new IllegalArgumentException(NAME_CANNOT_BE_EMPTY);
		}
		return tasks
				.stream()
				.filter(task -> ((Assignable) task).getAssignee().getName().equalsIgnoreCase(assigneeName))
				.collect(Collectors.toList());
	}

	private List<Task> filterFeedbacksAndNotAssigned() {
		return repository.getTasks()
				.stream()
				.filter(task -> task.getTaskType() != TaskType.FEEDBACK
						&& ((Assignable) task).getAssignee() != null
						&& ((Assignable) task).getAssignee().getName() != "Unassigned")
				.collect(Collectors.toList());
	}

	private boolean titleContains(String title) {
		for (Task task : repository.getTasks()) {
			if (task.getTitle().contains(title)) {
				return true;
			}
		}
		return false;
	}

}
