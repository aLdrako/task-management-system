package com.telerikacademy.tms.core;

import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.exceptions.ElementNotFoundException;
import com.telerikacademy.tms.exceptions.InvalidUserInputException;
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
	private final static String NO_SUCH_BOARD_IN_TEAM = "There is no Board with name '%s' in Team '%s'!";
	private int nextId;

	private final List<Team> teams = new ArrayList<>();
	private final List<Board> boards = new ArrayList<>();
	private final List<User> users = new ArrayList<>();
	private final List<Task> tasks = new ArrayList<>();
	private final List<Bug> bugs = new ArrayList<>();
	private final List<Story> stories = new ArrayList<>();
	private final List<Feedback> feedbacks = new ArrayList<>();

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
	public List<Task> getTasks() {
		return new ArrayList<>(tasks);
	}

	@Override
	public List<Bug> getBugs() {
		return new ArrayList<>(bugs);
	}

	@Override
	public List<Story> getStories() {
		return new ArrayList<>(stories);
	}

	@Override
	public List<Feedback> getFeedbacks() {
		return new ArrayList<>(feedbacks);
	}

	@Override
	public List<Board> getBoards() {
		return new ArrayList<>(boards);
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
		try {
			Bug bug = new BugImpl(++nextId, title, description, priority, severity);
			this.tasks.add(bug);
			this.bugs.add(bug);
			return bug;
		} catch (IllegalArgumentException e) {
			--nextId;
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public Story createStory(String title, String description, PriorityType priority, SizeType size) {
		try {
			Story story = new StoryImpl(++nextId, title, description, priority, size);
			this.tasks.add(story);
			this.stories.add(story);
			return story;
		} catch (IllegalArgumentException e) {
			--nextId;
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public Feedback createFeedback(String title, String description, Rating rating) {
		try {
			Feedback feedback = new FeedbackImpl(++nextId, title, description, rating);
			this.tasks.add(feedback);
			this.feedbacks.add(feedback);
			return feedback;
		} catch (IllegalArgumentException e) {
			--nextId;
			throw new IllegalArgumentException(e.getMessage());
		}
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
	@Override
	public Board findBoardByTask(Task task) {
		return getBoards().stream()
				.filter(board -> board.getTasks().contains(task))
				.findFirst()
				.orElseThrow(InvalidUserInputException::new);
	}
	@Override
	public Team findTeamByBoard(Board board) {
		return getTeams().stream()
				.filter(team -> team.getBoards().contains(board))
				.findFirst()
				.orElseThrow(InvalidUserInputException::new);
	}

	/**
	 * Use to check if name is unique for Team, User оr Board
	 */
	public boolean isNameUnique(String name) {
		boolean sameTeamName = getTeams().stream().noneMatch(n -> n.getName().equalsIgnoreCase(name));
		boolean sameBoardName = getTeams().stream().flatMap(team -> team.getBoards().stream())
				.noneMatch(n -> n.getName().equalsIgnoreCase(name));
		boolean sameUserName = getUsers().stream().noneMatch(n -> n.getName().equalsIgnoreCase(name));
		return sameTeamName && sameUserName && sameBoardName;
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
				.orElseThrow(() -> new ElementNotFoundException(format(NO_SUCH_BOARD_IN_TEAM, name, team.getName())));
	}
}
