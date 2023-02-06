package com.telerikacademy.tms.models.tasks.enums;

import com.telerikacademy.tms.exceptions.InvalidEnumArgumentException;

public enum PriorityType {
    HIGH,
    MEDIUM,
    LOW;

    @Override
    public String toString() {
        switch (this) {
            case HIGH:
                return "High";
            case MEDIUM:
                return "Medium";
            case LOW:
                return "Low";
            default:
                throw new InvalidEnumArgumentException("No such priority type");
        }
    }
}
