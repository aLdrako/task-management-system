package com.telerikacademy.tms.models.tasks;

import com.telerikacademy.tms.models.UserImpl;
import com.telerikacademy.tms.models.compositions.HistoryImpl;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Bug;
import com.telerikacademy.tms.models.tasks.enums.BugStatus;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BugImpl extends TaskBaseImpl implements Bug {
	private static final String BUG_UNASSIGNED = "Task is Unassigned";
	private final List<String> steps = new ArrayList<>();
	private PriorityType priority;
	private SeverityType severity;
	private User assignee = new UserImpl("Unassigned");

	public BugImpl(int id, String title, String description, PriorityType priority, SeverityType severity) {
		super(id, title, description);
		setPriority(priority);
		setSeverity(severity);
		setStatus(BugStatus.ACTIVE);
		populateHistory(new HistoryImpl(BUG_UNASSIGNED));
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
		addChangeToHistory("assignee", this.assignee.getName(), assignee.getName());
		this.assignee = assignee;
	}

	@Override
	public String toString() {
		String isAssigned = this.getAssignee() != null ? this.getAssignee().getName() : "Unassigned";
		StringBuilder doesHaveStepsToReproduce = new StringBuilder();

		if (this.getSteps().size() == 0) {
			doesHaveStepsToReproduce.append("Not specified");
		} else {
			doesHaveStepsToReproduce.append(System.lineSeparator()).append("\t-> ");
			doesHaveStepsToReproduce.append(this.getSteps().stream().map(s -> s.toString()).collect(Collectors.joining("\n\t-> ")));
		}

		return super.toString() + " | Priority: " + this.getPriority() +
				" | Severity: " + this.getSeverity() + " | Assignee: " + isAssigned +
				" | Steps to reproduce: " + doesHaveStepsToReproduce;
	}
}
