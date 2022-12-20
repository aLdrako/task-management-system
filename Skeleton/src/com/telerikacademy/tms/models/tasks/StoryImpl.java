package com.telerikacademy.tms.models.tasks;

import com.telerikacademy.tms.models.compositions.HistoryImpl;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Story;
import com.telerikacademy.tms.models.tasks.enums.*;

import static java.lang.String.format;

public class StoryImpl extends TaskBaseImpl implements Story {

	private PriorityType priority;
	private SizeType size;
	private User assignee;

	public StoryImpl(int id, String title, String description) {
		super(id, title, description);
		setStatus(StoryStatus.NOT_DONE);
		size = SizeType.SMALL;
	}

	@Override
	public PriorityType getPriority() {
		return priority;
	}

	@Override
	public SizeType getSize() {
		return size;
	}

	@Override
	public void setPriority(PriorityType priority) {
		this.priority = priority;
		populateHistory(new HistoryImpl(format("Priority changed to: %s", priority)));
	}

	@Override
	public void setSize(SizeType size) {
		this.size = size;
		populateHistory(new HistoryImpl(format("Size changed to: %s", size)));
	}

	@Override
	public User getAssignee() {
		return assignee;
	}

	@Override
	public void setAssignee(User assignee) {
		this.assignee = assignee;
		populateHistory(new HistoryImpl(format("Assigned to: %s", assignee.getName())));
	}
}
