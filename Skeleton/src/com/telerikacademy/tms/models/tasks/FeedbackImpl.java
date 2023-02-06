package com.telerikacademy.tms.models.tasks;

import com.telerikacademy.tms.models.tasks.contracts.Feedback;
import com.telerikacademy.tms.models.tasks.enums.FeedbackStatus;
import com.telerikacademy.tms.models.tasks.enums.Rating;
import com.telerikacademy.tms.models.tasks.enums.TaskType;

import static java.lang.String.format;

public class FeedbackImpl extends TaskBaseImpl implements Feedback {
    private static final String FEEDBACK_TO_STRING = "%s | Rating: %s";
    private Rating rating;

    public FeedbackImpl(int id, String title, String description, Rating rating) {
        super(id, title, description);
        setRating(rating);
        setStatus(FeedbackStatus.NEW);
        setTaskType(TaskType.FEEDBACK);
    }

    @Override
    public Rating getRating() {
        return rating;
    }

    @Override
    public void setRating(Rating rating) {
        addChangeToHistory("Rating", this.rating, rating);
        this.rating = rating;
    }

    @Override
    public String toString() {
        return format(FEEDBACK_TO_STRING, super.toString(), this.getRating());
    }
}
