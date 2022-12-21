package com.telerikacademy.tms.models.tasks.enums;

import com.telerikacademy.tms.exceptions.InvalidEnumArgument;
import com.telerikacademy.tms.models.tasks.contracts.Status;

public enum FeedbackStatus implements Status {
	NEW,
	UNSCHEDULED,
	SCHEDULED,
	DONE;

	@Override
	public String toString() {
		switch (this) {
			case NEW:
				return "New";
			case UNSCHEDULED:
				return "Unscheduled";
			case SCHEDULED:
				return "Scheduled";
			case DONE:
				return "Done";
			default:
				throw new InvalidEnumArgument("No such status");
		}
	}
}
