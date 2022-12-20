package com.telerikacademy.tms.models.tasks.enums;

import com.telerikacademy.tms.exceptions.InvalidEnumArgument;

public enum SeverityType {
	CRITICAL,
	MAJOR,
	MINOR;

	@Override
	public String toString() {
		switch (this) {
			case CRITICAL: return "Critical";
			case MAJOR: return "Major";
			case MINOR: return "Minor";
			default: throw new InvalidEnumArgument("No such severity type");
		}
	}
}
