package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.compositions.CommentImpl;
import com.telerikacademy.tms.models.compositions.contracts.Comment;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.contracts.Task;
import com.telerikacademy.tms.models.tasks.enums.Rating;

import java.util.List;

import static com.telerikacademy.tms.utils.ParsingHelpers.tryParseInt;
import static com.telerikacademy.tms.utils.ValidationHelpers.validateArgumentsCount;
import static java.lang.String.format;

public class AddComment implements Command {
	private static final int EXPECTED_NUMBER_PARAMETERS = 3;
	public static final String COMMENT_ADDED_SUCCESSFUL = "User %s added comment to task with ID %s";
	private final TaskManagementRepository repository;

	public AddComment(TaskManagementRepository repository) {
		this.repository = repository;
	}

	@Override
	public String execute(List<String> parameters) {
		//TODO to remove after test and implementation of all commands
		User user = repository.createUser("Alexa");
		repository.createFeedback("Good Feedback", "Some good feedback here", Rating.NINE);
		validateArgumentsCount(parameters, EXPECTED_NUMBER_PARAMETERS);
		int id = tryParseInt(parameters.get(0));
		String commentMessage = parameters.get(1).strip();
		String userName = parameters.get(2);

		return addComment(id, commentMessage, userName);
	}

	private String addComment(int id, String commentMessage, String userName) {
		Task task = repository.findElementById(repository.getTasks(), id);
		User user = repository.findElementByName(repository.getUsers(), userName);
		Comment comment = new CommentImpl(commentMessage, user.getName());
		task.addComment(comment);
		user.addCommentActivity(task);
		System.out.println(comment.getContent());

		return format(COMMENT_ADDED_SUCCESSFUL, user.getName(), task.getID());
	}
}
