package com.telerikacademy.tms.models.tasks.enums;

import com.telerikacademy.tms.exceptions.InvalidEnumArgumentException;

public enum TaskType {
	BUG,
	FEEDBACK,
	STORY;

	@Override
	public String toString() {
		switch (this) {
			case BUG:
				return "Bug";
			case FEEDBACK:
				return "Feedback";
			case STORY:
				return "Story";
			default:
				throw new InvalidEnumArgumentException("No such task type");
		}
	}
}
