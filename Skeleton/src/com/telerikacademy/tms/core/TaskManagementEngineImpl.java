package com.telerikacademy.tms.core;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.CommandFactory;
import com.telerikacademy.tms.core.contracts.Engine;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;

import java.util.*;


public class TaskManagementEngineImpl implements Engine {
	public static final String EMPTY_COMMAND_MESSAGE = "Command cannot be empty";
	public static final String TERMINATION_COMMAND = "Exit";
	private static final String COMMENT_OPEN_SYMBOL = "{{";
	private static final String COMMENT_CLOSE_SYMBOL = "}}";

	TaskManagementRepository taskManagementRepository;
	CommandFactory commandFactory;

	public TaskManagementEngineImpl() {
		taskManagementRepository = new TaskManagementRepositoryImpl();
		commandFactory = new CommandFactoryImpl();
	}

	@Override
	public void start() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String input = scanner.nextLine();
			try {
				if (input.isBlank()) {
					print(EMPTY_COMMAND_MESSAGE);
					continue;
				}
				if (input.equalsIgnoreCase(TERMINATION_COMMAND)) {
					break;
				}
				extractInput(input);
			} catch (Exception e) {
				if (e.getMessage() != null && !e.getMessage().isEmpty()) {
					print(e.getMessage());
				} else {
					print(e.toString());
				}
			}
		}
	}

	private void print(String messageToPrint) {
		System.out.println(messageToPrint);
	}

	private void extractInput(String input) {
		String commandName = commandName(input);
		List<String> parameters = extractCommandParameters(input);
		Command command = commandFactory.createCommandFromCommandName(commandName, taskManagementRepository);
		String executionResult = command.execute(parameters);
		print(executionResult);
	}

	private List<String> extractCommandParameters(String inputLine) {
		if (inputLine.contains(COMMENT_OPEN_SYMBOL)) {
			return extractCommentParameters(inputLine);
		}
		String[] commandParts = inputLine.split(" ");

		return new ArrayList<>(Arrays.asList(commandParts).subList(1, commandParts.length));

//		String[] commandParts = inputLine.split(" ");
//		List<String> parameters = new ArrayList<>();
//		for (int i = 1; i < commandParts.length; i++) {
//			parameters.add(commandParts[i]);
//		}
//		return parameters;
	}

	public List<String> extractCommentParameters(String fullCommand) {
		List<String> parameters = new ArrayList<>();
		while (fullCommand.contains(COMMENT_OPEN_SYMBOL)) {
			int indexOfOpenComment = fullCommand.indexOf(COMMENT_OPEN_SYMBOL);
			int indexOfCloseComment = fullCommand.indexOf(COMMENT_CLOSE_SYMBOL);
			parameters.addAll(Arrays.asList(fullCommand.substring(0, indexOfOpenComment).split(" ")));
			parameters.add(fullCommand.substring(indexOfOpenComment + COMMENT_OPEN_SYMBOL.length(), indexOfCloseComment));
			//    fullCommand = fullCommand.replaceAll("\\{\\{.+(?=}})}}", "");
			fullCommand = fullCommand.replace(fullCommand.substring(0, indexOfCloseComment + 2), "");
		}
		parameters.addAll(Arrays.asList(fullCommand.split(" ")));
		parameters.removeAll(Collections.singleton(""));
		parameters.remove(0);
		return parameters;
	}


	private String commandName(String input) {
		return input.split(" ")[0];
	}
}
