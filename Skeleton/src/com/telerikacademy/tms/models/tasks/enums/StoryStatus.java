package com.telerikacademy.tms.models.tasks.enums;

import com.telerikacademy.tms.exceptions.InvalidEnumArgument;
import com.telerikacademy.tms.models.tasks.contracts.Status;

public enum StoryStatus implements Status {
	NOT_DONE,
	IN_PROGRESS,
	DONE;

	@Override
	public String toString() {
		switch (this) {
			case NOT_DONE: return "Not Done";
			case IN_PROGRESS: return "InProgress";
			case DONE: return "Done";
			default: throw new InvalidEnumArgument("No such status");
		}
	}
}
