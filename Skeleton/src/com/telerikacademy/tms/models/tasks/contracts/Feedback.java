package com.telerikacademy.tms.models.tasks.contracts;

import com.telerikacademy.tms.models.tasks.enums.Rating;

public interface Feedback extends Task {

	Rating getRating();

	void setRating(Rating rating);
}
