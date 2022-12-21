package com.telerikacademy.tms.models.tasks;

import com.telerikacademy.tms.models.compositions.HistoryImpl;
import com.telerikacademy.tms.models.compositions.contracts.Comment;
import com.telerikacademy.tms.models.compositions.contracts.History;
import com.telerikacademy.tms.models.tasks.contracts.Status;
import com.telerikacademy.tms.models.tasks.contracts.Task;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.tms.utils.ValidationHelpers.*;
import static java.lang.String.format;

public abstract class TaskBaseImpl implements Task {

	private static final int TITLE_MIN_LEN = 10;
	private static final int TITLE_MAX_LEN = 50;
	private static final int DESCRIPTION_MIN_LEN = 10;
	private static final int DESCRIPTION_MAX_LEN = 500;
	public static final String TEXT_LEN_ERR = "%s should be between %s and %s symbols.";
	public static final String CHANGE_MESSAGE = "The %s of item with ID %d switched from '%s' to '%s'";

	private final int id;
	private String title;
	private String description;
	private final List<Comment> comments = new ArrayList<>();
	private final List<History> changesHistory = new ArrayList<>();
	private Status status;

	public TaskBaseImpl(int id, String title, String description) {
		this.id = id;
		setTitle(title);
		setDescription(description);
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public String getTitle() {
		return title;
	}

	private void setTitle(String title) {
		validateInRange(title.length(), TITLE_MIN_LEN, TITLE_MAX_LEN,
				format(TEXT_LEN_ERR, "Title", TITLE_MIN_LEN, TITLE_MAX_LEN));
		this.title = title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	private void setDescription(String description) {
		validateInRange(title.length(), DESCRIPTION_MIN_LEN, DESCRIPTION_MAX_LEN,
				format(TEXT_LEN_ERR, "Description", DESCRIPTION_MIN_LEN, DESCRIPTION_MAX_LEN));
		this.description = description;
	}

	@Override
	public List<Comment> getComments() {
		return new ArrayList<>(comments);
	}

	public void addComment(Comment comment) {
		this.comments.add(comment);
		this.populateHistory(new HistoryImpl(format("Comment added to task: %s %s", this.getID(), this.getTitle())));
	}

	@Override
	public List<History> getHistories() {
		return new ArrayList<>(changesHistory);
	}

	@Override
	public void populateHistory(History history) {
		this.changesHistory.add(history);
	}

	@Override
	public Status getStatus() {
		return this.status;
	}

	@Override
	public <T extends Status> void setStatus(T status) {
		addChangeToHistory("status", this.status, status);
		this.status = status;
	}

	protected <T> void addChangeToHistory(String type, T valueBefore, T valueAfter) {
		if (valueBefore != null && valueBefore != valueAfter) {
			if (valueBefore instanceof Number && (Integer) valueBefore == 0) {
				return;
			}
			changesHistory.add(new HistoryImpl(format(CHANGE_MESSAGE, type, getID(), valueBefore, valueAfter)));
		}
	}

}
