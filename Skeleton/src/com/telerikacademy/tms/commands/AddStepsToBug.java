package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.tasks.contracts.Bug;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.tms.utils.ParsingHelpers.tryParseInt;
import static com.telerikacademy.tms.utils.ValidationHelpers.validateArgumentCountRange;
import static java.lang.String.format;

public class AddStepsToBug implements Command {
	private static final int EXPECTED_NUMBER_PARAMETERS_MIN = 2;
	private static final int EXPECTED_NUMBER_PARAMETERS_MAX = 10;
	private static final String STEPS_ADDED_SUCCESSFUL = "Steps to reproduce added to Bug with ID -> [%s]";
	private static final String BUG_ALREADY_HAS_STEPS = "Bug with ID -> [%s] already has defined steps to reproduce";
	private final TaskManagementRepository repository;

	public AddStepsToBug(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		validateArgumentCountRange(parameters, EXPECTED_NUMBER_PARAMETERS_MIN, EXPECTED_NUMBER_PARAMETERS_MAX);
		int id = tryParseInt(parameters.get(0));
		List<String> steps = new ArrayList<>();
		for (int i = 1; i < parameters.size(); i++) {
			steps.add(parameters.get(i));
		}
		return addStepsToReproduce(id, steps);
	}

	private String addStepsToReproduce(int id, List<String> steps) {
		Bug bug = repository.findTaskById(repository.getBugs(), id, "Bug");

		if (bug.getSteps().size() != 0)
			throw new IllegalArgumentException(format(BUG_ALREADY_HAS_STEPS, bug.getID()));
		for (String step : steps) {
			bug.addStep(step.strip());
		}

		return format(STEPS_ADDED_SUCCESSFUL, bug.getID());
	}
}
