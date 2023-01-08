package com.telerikacademy.tms.utils;

import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Assignable;
import com.telerikacademy.tms.models.tasks.contracts.Task;

import java.util.List;
import java.util.stream.Collectors;

public class FilterHelpers {

	private static final String TITLE_DOES_NOT_EXIST = "There is not task that contains the given title.";

	public static <E extends Enum<E>, T extends Task> List<T> filterByStatus(String parameter, List<T> list, Class<E> type) {
		E status = ParsingHelpers.tryParseEnum(parameter, type);
		return list.stream().filter(value -> value.getStatus() == status).collect(Collectors.toList());
	}

	public static <T extends Assignable> List<T> filterByAssignee(String parameter, List<T> list, TaskManagementRepository repository) {
		User user = repository.findElementByName(repository.getUsers(), parameter);
		return list.stream().filter(task -> task.getAssignee() == user)
				.collect(Collectors.toList());
	}

	public static <T extends Task> List<T> filterByTitle(String parameter, List<T> list) {
		if (list.stream().noneMatch(task -> task.getTitle().toLowerCase().contains(parameter.toLowerCase().strip()))) {
			throw new InvalidUserInputException(TITLE_DOES_NOT_EXIST);
		}
		return list.stream()
				.filter(task -> task.getTitle().toLowerCase().contains(parameter.toLowerCase().strip()))
				.collect(Collectors.toList());
	}
}
