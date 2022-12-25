package com.telerikacademy.tms.models.tasks;

import com.telerikacademy.tms.models.tasks.contracts.Feedback;
import com.telerikacademy.tms.models.tasks.enums.FeedbackStatus;
import com.telerikacademy.tms.models.tasks.enums.Rating;
import com.telerikacademy.tms.models.tasks.enums.TaskType;

public class FeedbackImpl extends TaskBaseImpl implements Feedback {
	private Rating rating;

	public FeedbackImpl(int id, String title, String description, Rating rating) {
		super(id, title, description);
		setRating(rating);
		setStatus(FeedbackStatus.NEW);
	}

	@Override
	public Rating getRating() {
		return rating;
	}

	@Override
	public void setRating(Rating rating) {
		addChangeToHistory("rating", this.rating, rating);
		this.rating = rating;
	}

	@Override
	public String toString() {
		return super.toString() + " | Rating: " + this.getRating();
	}
}
