package com.telerikacademy.tms.models.tasks.contracts;

import com.telerikacademy.tms.models.contracts.User;

public interface Assignable {
	User getAssignee();

	void setAssignee(User assignee);
}
