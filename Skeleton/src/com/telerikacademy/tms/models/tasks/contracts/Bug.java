package com.telerikacademy.tms.models.tasks.contracts;

import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;

import java.util.List;

public interface Bug extends Task, Assignable {

	List<String> getSteps();

	PriorityType getPriority();

	SeverityType getSeverity();

	void setPriority(PriorityType priority);

	void setSeverity(SeverityType severity);

}
