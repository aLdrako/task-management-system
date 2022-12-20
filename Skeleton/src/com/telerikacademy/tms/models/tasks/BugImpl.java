package com.telerikacademy.tms.models.tasks;

import com.telerikacademy.tms.models.compositions.HistoryImpl;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Bug;
import com.telerikacademy.tms.models.tasks.enums.BugStatus;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class BugImpl extends TaskBaseImpl implements Bug {

	private final List<String> steps;
	private PriorityType priority;
	private SeverityType severity;
	private User assignee;

	public BugImpl(int id, String title, String description) {
		super(id, title, description);
		this.steps = new ArrayList<>();
		setStatus(BugStatus.ACTIVE);
	}

	@Override
	public List<String> getSteps() {
		return new ArrayList<>(steps);
	}

	@Override
	public void addStep(String step) {
		this.steps.add(step);
	}

	@Override
	public PriorityType getPriority() {
		return priority;
	}

	@Override
	public void setPriority(PriorityType priority) {
		addChangeToHistory("priority", this.priority, priority);
		this.priority = priority;
	}

	@Override
	public SeverityType getSeverity() {
		return severity;
	}

	@Override
	public void setSeverity(SeverityType severity) {
		addChangeToHistory("severity", this.severity, severity);
		this.severity = severity;
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

}