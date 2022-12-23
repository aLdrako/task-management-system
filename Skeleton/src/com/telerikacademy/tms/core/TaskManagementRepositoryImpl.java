package com.telerikacademy.tms.core;

import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.ElementNotFoundException;
import com.telerikacademy.tms.models.BoardImpl;
import com.telerikacademy.tms.models.TeamImpl;
import com.telerikacademy.tms.models.UserImpl;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.BugImpl;
import com.telerikacademy.tms.models.tasks.FeedbackImpl;
import com.telerikacademy.tms.models.tasks.StoryImpl;
import com.telerikacademy.tms.models.tasks.contracts.*;
import com.telerikacademy.tms.models.tasks.enums.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class TaskManagementRepositoryImpl implements TaskManagementRepository {
	private static final String NO_RECORD_ID = "No task with ID %d";
	private final static String NO_SUCH_ELEMENT = "There is no User or Team with name %s!";
	private int nextId;

	private final List<Team> teams = new ArrayList<>();
	private final List<User> users = new ArrayList<>();
	private final List<Board> boards = new ArrayList<>();
	private final List<Task> tasks = new ArrayList<>();

	public TaskManagementRepositoryImpl() {
		this.nextId = 0;
	}

	@Override
	public List<Team> getTeams() {
		return new ArrayList<>(teams);
	}

	@Override
	public List<User> getUsers() {
		return new ArrayList<>(users);
	}

	@Override
	public List<Board> getBoards() {
		return new ArrayList<>(boards);
	}

	@Override
	public List<Task> getTasks() {
		return new ArrayList<>(tasks);
	}

	@Override
	public Team createTeam(String name) {
		Team team = new TeamImpl(name);
		this.teams.add(team);
		return team;
	}

	@Override
	public User createUser(String name) {
		User user = new UserImpl(name);
		this.users.add(user);
		return user;
	}

	@Override
	public Board createBoard(String name) {
		Board board = new BoardImpl(name);
		this.boards.add(board);
		return board;
	}

	@Override
	public Bug createBug(String title, String description, PriorityType priority, SeverityType severity) {
		Bug bug = new BugImpl(++nextId, title, description, priority, severity);
		bug.setTaskType(TaskType.BUG);
		this.tasks.add(bug);
		return bug;
	}

	@Override
	public Story createStory(String title, String description, PriorityType priority, SizeType size) {
		Story story = new StoryImpl(++nextId, title, description, priority, size);
		story.setTaskType(TaskType.STORY);
		this.tasks.add(story);
		return story;
	}

	@Override
	public Feedback createFeedback(String title, String description, Rating rating) {
		Feedback feedback = new FeedbackImpl(++nextId, title, description, rating);
		feedback.setTaskType(TaskType.FEEDBACK);
		this.tasks.add(feedback);
		return feedback;
	}

	/**
	 * Search by ID all elements which extends Task interface (Bug, Story, Feedback)
	 */
	@Override
	public <T extends Task> T findElementById(List<T> elements, int id) {
		return elements.stream()
				.filter(el -> el.getID() == id)
				.findFirst()
				.orElseThrow(() -> new ElementNotFoundException(format(NO_RECORD_ID, id)));
	}

	/**
	 * Use to check if name is unique for Team and User
	 */
	public boolean isNameUnique(String name) {
		boolean sameTeamName = getTeams().stream().noneMatch(n -> n.getName().equalsIgnoreCase(name));
		boolean sameUserName = getUsers().stream().noneMatch(n -> n.getName().equalsIgnoreCase(name));
		return sameTeamName && sameUserName;
	}

	/**
	 * Use to check if board name is unique in specified team
	 */
	public boolean isBoardNameUniqueInTeam(Team team, String name) {
		return team.getBoards().stream().noneMatch(board -> board.getName().equalsIgnoreCase(name));
	}

	/**
	 * Use to search Teams and Users by name (unique in application)
	 */
	public <T extends Nameable> T findElementByName(List<T> elements, String name) {
		return elements.stream()
				.filter(el -> el.getName().equalsIgnoreCase(name))
				.findFirst()
				.orElseThrow(() -> new ElementNotFoundException(String.format(NO_SUCH_ELEMENT, name)));
	}

	/**
	 * Use to search Boards by name, by passing specific team (unique in teams)
	 */
	public Board findBoardByNameInTeam(Team team, String name) {
		return team.getBoards().stream()
				.filter(board -> board.getName().equalsIgnoreCase(name))
				.findFirst()
				.orElseThrow(() -> new ElementNotFoundException(format(NO_SUCH_ELEMENT, name)));
	}
}
