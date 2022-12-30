package com.telerikacademy.tms.models.tasks;

import com.telerikacademy.tms.models.UserImpl;
import com.telerikacademy.tms.models.compositions.HistoryImpl;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Bug;
import com.telerikacademy.tms.models.tasks.enums.BugStatus;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;
import com.telerikacademy.tms.models.tasks.enums.TaskType;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.lang.String.join;

public class BugImpl extends TaskBaseImpl implements Bug {
	private static final String BUG_UNASSIGNED = "Task is Unassigned";
	private static final String BUG_TO_STRING = "%s | Priority: %s | Severity: %s | Assignee: %s | Steps to reproduce: %s";
	private List<String> steps;
	private PriorityType priority;
	private SeverityType severity;
	private User assignee = new UserImpl("Unassigned");

	public BugImpl(int id, String title, String description, PriorityType priority, SeverityType severity, List<String> steps) {
		super(id, title, description);
		setPriority(priority);
		setSeverity(severity);
		setStatus(BugStatus.ACTIVE);
		setTaskType(TaskType.BUG);
		populateHistory(new HistoryImpl(BUG_UNASSIGNED));
		setSteps(steps);
	}

	@Override
	public List<String> getSteps() {
		return new ArrayList<>(steps);
	}

	private void setSteps(List<String> steps) {
		this.steps = new ArrayList<>(steps);
		populateHistory(new HistoryImpl(format("Steps to reproduce -> %s", join(" -> ", steps))));
	}

	@Override
	public PriorityType getPriority() {
		return priority;
	}

	@Override
	public void setPriority(PriorityType priority) {
		addChangeToHistory("Priority", this.priority, priority);
		this.priority = priority;
	}

	@Override
	public SeverityType getSeverity() {
		return severity;
	}

	@Override
	public void setSeverity(SeverityType severity) {
		addChangeToHistory("Severity", this.severity, severity);
		this.severity = severity;
	}

	@Override
	public User getAssignee() {
		return assignee;
	}

	@Override
	public void setAssignee(User assignee) {
		addChangeToHistory("Assignee", this.assignee.getName(), assignee.getName());
		this.assignee = assignee;
	}

	@Override
	public String toString() {

		return format(BUG_TO_STRING,
				super.toString(), this.getPriority(), this.getSeverity(), this.getAssignee().getName(),
				"\n\t".concat(join("\n\t", this.getSteps())));
	}
}
