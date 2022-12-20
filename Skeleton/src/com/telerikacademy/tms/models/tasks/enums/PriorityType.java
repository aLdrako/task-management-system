package com.telerikacademy.tms.models.tasks.enums;

import com.telerikacademy.tms.exceptions.InvalidEnumArgument;

public enum PriorityType {
	HIGH,
	MEDIUM,
	LOW;

	@Override
	public String toString() {
		switch (this) {
			case HIGH: return "High";
			case MEDIUM: return "Medium";
			case LOW: return "Low";
			default: throw new InvalidEnumArgument("No such priority type");
		}
	}
}
