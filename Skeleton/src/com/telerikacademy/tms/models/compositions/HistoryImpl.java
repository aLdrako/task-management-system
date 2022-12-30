package com.telerikacademy.tms.models.compositions;

import com.telerikacademy.tms.models.compositions.contracts.History;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.String.format;

public class HistoryImpl implements History {
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm:ss");
	private String description;
	private final LocalDateTime timestamp = LocalDateTime.now();

	public HistoryImpl(String description) {
		setDescription(description);
	}

	@Override
	public String getDescription() {
		return description;
	}

	private void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return format("[%s] %s", timestamp.format(formatter), this.getDescription());
	}
}
