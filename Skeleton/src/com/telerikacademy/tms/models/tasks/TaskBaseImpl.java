package com.telerikacademy.tms.models.tasks;

import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.compositions.HistoryImpl;
import com.telerikacademy.tms.models.compositions.contracts.Comment;
import com.telerikacademy.tms.models.compositions.contracts.History;
import com.telerikacademy.tms.models.tasks.contracts.Status;
import com.telerikacademy.tms.models.tasks.contracts.Task;
import com.telerikacademy.tms.models.tasks.enums.TaskType;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.tms.utils.ValidationHelpers.validateInRange;
import static java.lang.String.format;

public abstract class TaskBaseImpl implements Task {
	private static final int TITLE_MIN_LEN = 10;
	private static final int TITLE_MAX_LEN = 50;
	private static final int DESCRIPTION_MIN_LEN = 10;
	private static final int DESCRIPTION_MAX_LEN = 500;
	private static final String NEW_INSTANCE_MESSAGE = "Task with ID -> [%s] was created.";
	private static final String TEXT_LEN_ERR = "%s should be between %s and %s symbols.";
	private static final String CHANGE_MESSAGE = "The '%s' of item with ID -> [%d] switched from {%s} to {%s}";
	private static final String TASK_COMMENT_ADDED = "Comment added to task.";
	private static final String TASK_TO_STRING = "%s: ID -> [%s] '%s' | Status: %s";

	private final int id;
	private String title;
	private String description;
	private final List<Comment> comments = new ArrayList<>();
	private final List<History> changesHistory = new ArrayList<>();
	private Status status;
	private TaskType taskType;

	public TaskBaseImpl(int id, String title, String description) {
		this.id = id;
		setTitle(title);
		setDescription(description);
		this.populateHistory(new HistoryImpl(format(NEW_INSTANCE_MESSAGE, id)));
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public TaskType getTaskType() {
		return taskType;
	}

	@Override
	public Status getStatus() {
		return this.status;
	}

	@Override
	public List<Comment> getComments() {
		return new ArrayList<>(comments);
	}

	@Override
	public List<History> getHistories() {
		return new ArrayList<>(changesHistory);
	}

	private void setDescription(String description) {
		validateInRange(description.length(), DESCRIPTION_MIN_LEN, DESCRIPTION_MAX_LEN,
				format(TEXT_LEN_ERR, "Description", DESCRIPTION_MIN_LEN, DESCRIPTION_MAX_LEN));
		this.description = description;
	}

	private void setTitle(String title) {
		validateInRange(title.length(), TITLE_MIN_LEN, TITLE_MAX_LEN,
				format(TEXT_LEN_ERR, "Title", TITLE_MIN_LEN, TITLE_MAX_LEN));
		this.title = title;
	}

	@Override
	public <T extends Status> void setStatus(T status) {
		addChangeToHistory("Status", this.status, status);
		this.status = status;
	}

	protected void setTaskType(TaskType ts) {
		taskType = ts;
	}

	@Override
	public void addComment(Comment comment) {
		this.comments.add(comment);
		this.populateHistory(new HistoryImpl(TASK_COMMENT_ADDED));
	}

	protected <T> void addChangeToHistory(String type, T valueBefore, T valueAfter) {
		if (valueBefore != null && !valueBefore.equals(valueAfter)) {
			changesHistory.add(new HistoryImpl(format(CHANGE_MESSAGE, type, getID(), valueBefore, valueAfter)));
		}
		if (valueBefore != null && valueBefore.equals(valueAfter)) {
			throw new InvalidUserInputException();
		}
	}

	protected void populateHistory(History history) {
		this.changesHistory.add(history);
	}

	@Override
	public int compareTo(Task o) {
		return getTitle().compareTo(o.getTitle());
	}

	@Override
	public String toString() {
		return format(TASK_TO_STRING,
				this.getClass().getInterfaces()[0].getSimpleName(),
				this.getID(), this.getTitle(), this.getStatus());
	}
}
