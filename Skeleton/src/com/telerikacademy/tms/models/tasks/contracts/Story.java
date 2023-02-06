package com.telerikacademy.tms.models.tasks.contracts;

import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SizeType;

public interface Story extends Task, Assignable {

    PriorityType getPriority();

    SizeType getSize();

    void setPriority(PriorityType priority);

    void setSize(SizeType size);

}
