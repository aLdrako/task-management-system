package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.tasks.contracts.Assignable;
import com.telerikacademy.tms.models.tasks.contracts.Task;
import com.telerikacademy.tms.models.tasks.enums.BugStatus;
import com.telerikacademy.tms.models.tasks.enums.StoryStatus;
import com.telerikacademy.tms.models.tasks.enums.TaskType;
import com.telerikacademy.tms.utils.TaskSorterByTitle;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ListTasksWithAssignee implements Command {
	private static final String FILTER_BY_ASSIGNEE = "FilterByAssignee";
	private static final String FILTER_BY_STATUS = "FilterByStatus";
	private static final String SORT_BY_TITLE = "SortByTitle";
	private static final String NAME_CANNOT_BE_EMPTY = "Assignee name field cannot be empty";
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
			return generateAllTasksList(tasks);
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
			}
		}
		if (filterByAssignee) {
			tasks = filterByAssignee(tasks, assigneeName);
		}
		if (filterByStatus) {
			tasks = filterByStatus(tasks, statusValue);
		}
		if (sortByTitle) {
			tasks = sortByTitle(tasks);
		}

		return generateAllTasksList(tasks);
	}

	private List<Task> sortByTitle(List<Task> tasks) {
		Collections.sort(tasks, new TaskSorterByTitle());
		return tasks;
	}

	private List<Task> filterByStatus(List<Task> tasks, String statusValue) {
		return tasks
				.stream()
				.filter(task ->
						task.getStatus() == Enum.valueOf(BugStatus.class, statusValue) ||
								task.getStatus() == Enum.valueOf(StoryStatus.class, statusValue))
				.collect(Collectors.toList());
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
				.filter(task -> task.getTaskType() != TaskType.FEEDBACK && ((Assignable) task).getAssignee() != null)
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

	private String generateAllTasksList(List<Task> tasks) {
		String s = "";
		for (Task task : tasks) {
			s += generateTaskListing(task, ((Assignable) task).getAssignee().getName());
		}
		return s;
	}

	private String generateTaskListing(Task task, String assignee) {
		return String.format("%s\t%s\t%s\t%s\n", task.getTaskType(), task.getTitle(), task.getStatus(), assignee);
	}

//	private String listTaskWithAssignee(String title, String status, String assignee) {
//		return String.format("Task %s with status %s is assigned to %s", title, status, assignee);
//	}

}
