package com.telerikacademy.tms.models.tasks;

import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Story;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SizeType;
import com.telerikacademy.tms.models.tasks.enums.StoryStatus;
import com.telerikacademy.tms.models.tasks.enums.TaskType;

public class StoryImpl extends TaskBaseImpl implements Story {
	private PriorityType priority;
	private SizeType size;
	private User assignee;

	public StoryImpl(int id, String title, String description, PriorityType priority, SizeType size) {
		super(id, title, description);
		setPriority(priority);
		setSize(size);
		setStatus(StoryStatus.NOTDONE);
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
		addChangeToHistory("priority", this.priority, priority);
		this.priority = priority;
	}

	@Override
	public void setSize(SizeType size) {
		addChangeToHistory("size", this.size, size);
		this.size = size;
	}

	@Override
	public User getAssignee() {
		return assignee;
	}

	@Override
	public void setAssignee(User assignee) {
		addChangeToHistory("assignee", this.assignee, assignee);
		this.assignee = assignee;
	}

	@Override
	public String toString() {
		String isAssigned = this.getAssignee() != null ? this.getAssignee().getName() : "Unassigned";

		return super.toString() + " | Priority: " + this.getPriority() +
				" | Size: " + this.getSize() + " | Assignee: " + isAssigned;
	}

	@Override
	public TaskType getTaskType() {
		return null;
	}

	@Override
	public void setTaskType(TaskType ts) {

	}
}
