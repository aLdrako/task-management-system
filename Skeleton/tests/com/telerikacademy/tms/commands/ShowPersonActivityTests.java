package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import org.junit.jupiter.api.BeforeEach;

public class ShowPersonActivityTests {

	private TaskManagementRepository repository;
	private Command command;


	@BeforeEach
	public void before() {
		repository = new TaskManagementRepositoryImpl();
		command = new ShowPersonActivity(repository);
	}

}
