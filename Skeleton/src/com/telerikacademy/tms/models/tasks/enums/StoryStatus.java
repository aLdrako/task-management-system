package com.telerikacademy.tms.models.tasks.enums;

import com.telerikacademy.tms.exceptions.InvalidEnumArgumentException;
import com.telerikacademy.tms.models.tasks.contracts.Status;

public enum StoryStatus implements Status {
	NOT_DONE,
	INPROGRESS,
	DONE;

	@Override
	public String toString() {
		switch (this) {
			case NOT_DONE:
				return "Not Done";
			case INPROGRESS:
				return "InProgress";
			case DONE:
				return "Done";
			default:
				throw new InvalidEnumArgumentException("No such status");
		}
	}
}
