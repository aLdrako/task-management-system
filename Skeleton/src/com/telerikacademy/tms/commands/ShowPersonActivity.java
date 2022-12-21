package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;

import java.util.List;

public class ShowPersonActivity implements Command {
	private final TaskManagementRepository repository;

	public ShowPersonActivity(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		return null;
	}
}