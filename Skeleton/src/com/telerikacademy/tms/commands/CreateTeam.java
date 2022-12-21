package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;

import java.util.List;

public class CreateTeam implements Command {
	private final TaskManagementRepository repository;

	public CreateTeam(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		return null;
	}
}