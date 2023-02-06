package com.telerikacademy.tms.models.tasks.enums;

import com.telerikacademy.tms.exceptions.InvalidEnumArgumentException;

public enum SizeType {
    LARGE,
    MEDIUM,
    SMALL;

    @Override
    public String toString() {
        switch (this) {
            case LARGE:
                return "Large";
            case MEDIUM:
                return "Medium";
            case SMALL:
                return "Small";
            default:
                throw new InvalidEnumArgumentException("No such size type");
        }
    }
}
