package com.telerikacademy.tms.models.tasks.enums;

import com.telerikacademy.tms.exceptions.InvalidEnumArgumentException;
import com.telerikacademy.tms.models.tasks.contracts.Status;

public enum BugStatus implements Status {
	ACTIVE,
	FIXED;

	@Override
	public String toString() {
		switch (this) {
			case ACTIVE:
				return "Active";
			case FIXED:
				return "Fixed";
			default:
				throw new InvalidEnumArgumentException("No such status");
		}
	}
}
