package com.telerikacademy.tms.commands.bugs;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;

import java.util.List;

public class AddStepsToReproduceBugCommand implements Command {

	public AddStepsToReproduceBugCommand(TaskManagementRepository repository) {
	}
	@Override
	public String execute(List<String> parameters) {
		return null;
	}
}
