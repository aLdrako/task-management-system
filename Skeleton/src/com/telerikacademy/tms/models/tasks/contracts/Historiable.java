package com.telerikacademy.tms.models.tasks.contracts;

import com.telerikacademy.tms.models.compositions.contracts.History;

import java.util.List;

public interface Historiable {

	List<History> getHistories();

	void populateHistory(History history);
}
