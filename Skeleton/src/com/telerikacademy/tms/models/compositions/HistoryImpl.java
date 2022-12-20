package com.telerikacademy.tms.models.compositions;

import com.telerikacademy.tms.models.compositions.contracts.History;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.String.format;

public class HistoryImpl implements History {
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm:ss");
	private final String description;
	private final LocalDateTime timestamp;

	public HistoryImpl(String description) {
		this.description = description;
		this.timestamp = LocalDateTime.now();
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String getHistory() {
		return format("[%s] %s", timestamp.format(formatter), this.getDescription());
	}
}
