package com.telerikacademy.tms.models.tasks.contracts;

public interface Feedback extends Task {

	int getRating();

	void setRating(int rating);
}
