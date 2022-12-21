package com.telerikacademy.tms.models.tasks;

import com.telerikacademy.tms.models.tasks.contracts.Feedback;
import com.telerikacademy.tms.models.tasks.enums.FeedbackStatus;
import com.telerikacademy.tms.utils.ValidationHelpers;

import static java.lang.String.format;

public class FeedbackImpl extends TaskBaseImpl implements Feedback {

	private static final int RATING_MIN = 1;
	private static final int RATING_MAX = 10;

	private static final String RATING_RANGE_ERR = format(
			"Rating must be between %d and %d.",
			RATING_MIN,
			RATING_MAX);

	private int rating;

	public FeedbackImpl(int id, String title, String description, int rating) {
		super(id, title, description);
		setRating(rating);
		setStatus(FeedbackStatus.NEW);
	}

	@Override
	public int getRating() {
		return rating;
	}

	@Override
	public void setRating(int rating) {
		ValidationHelpers.validateInRange(rating, RATING_MIN, RATING_MAX, RATING_RANGE_ERR);
		addChangeToHistory("rating", this.rating, rating);
		this.rating = rating;
	}

}
