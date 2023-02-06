package com.telerikacademy.tms.models.tasks;

import com.telerikacademy.tms.models.UserImpl;
import com.telerikacademy.tms.models.compositions.HistoryImpl;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Story;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SizeType;
import com.telerikacademy.tms.models.tasks.enums.StoryStatus;
import com.telerikacademy.tms.models.tasks.enums.TaskType;

import static java.lang.String.format;

public class StoryImpl extends TaskBaseImpl implements Story {
    private static final String STORY_UNASSIGNED = "Task is Unassigned";
    private static final String STORY_TO_STRING = "%s | Priority: %s | Size: %s | Assignee: %s";
    private PriorityType priority;
    private SizeType size;
    private User assignee = new UserImpl("Unassigned");

    public StoryImpl(int id, String title, String description, PriorityType priority, SizeType size) {
        super(id, title, description);
        setPriority(priority);
        setSize(size);
        setStatus(StoryStatus.NOT_DONE);
        setTaskType(TaskType.STORY);
        populateHistory(new HistoryImpl(STORY_UNASSIGNED));
    }

    @Override
    public PriorityType getPriority() {
        return priority;
    }

    @Override
    public SizeType getSize() {
        return size;
    }

    @Override
    public User getAssignee() {
        return assignee;
    }

    @Override
    public void setPriority(PriorityType priority) {
        addChangeToHistory("Priority", this.priority, priority);
        this.priority = priority;
    }

    @Override
    public void setSize(SizeType size) {
        addChangeToHistory("Size", this.size, size);
        this.size = size;
    }


    @Override
    public void setAssignee(User assignee) {
        addChangeToHistory("Assignee", this.assignee.getName(), assignee.getName());
        this.assignee = assignee;
    }

    @Override
    public String toString() {
        return format(STORY_TO_STRING,
                super.toString(), this.getPriority(), this.getSize(), this.getAssignee().getName());
    }
}
