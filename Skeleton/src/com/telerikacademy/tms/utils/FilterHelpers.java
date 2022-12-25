package com.telerikacademy.tms.utils;

import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Assignable;
import com.telerikacademy.tms.models.tasks.contracts.Task;

import java.util.List;
import java.util.stream.Collectors;

public class FilterHelpers {

	public static <E extends Enum<E>, T extends Task> List<T> filterByStatus(String parameter, List<T> list, Class<E> type) {
		E status = ParsingHelpers.tryParseEnum(parameter, type);
		return list.stream().filter(value -> value.getStatus() == status).collect(Collectors.toList());
	}

	public static <T extends Assignable> List<T> filterByAssignee(String parameter, List<T> list, TaskManagementRepository repository) {
		User user = repository.findElementByName(repository.getUsers(), parameter);
		return list.stream().filter(task -> task.getAssignee() == user)
				.collect(Collectors.toList());
	}


}
