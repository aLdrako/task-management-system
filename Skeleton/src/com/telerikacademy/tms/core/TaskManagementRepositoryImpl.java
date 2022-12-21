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
import com.telerikacademy.tms.models.tasks.contracts.Bug;
import com.telerikacademy.tms.models.tasks.contracts.Feedback;
import com.telerikacademy.tms.models.tasks.contracts.Story;
import com.telerikacademy.tms.models.tasks.contracts.Task;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;
import com.telerikacademy.tms.models.tasks.enums.SizeType;

import java.util.ArrayList;
import java.util.List;

public class TaskManagementRepositoryImpl implements TaskManagementRepository {
	public static final String NO_RECORD_ID = "No record with ID %d";
	private final static String NO_SUCH_ELEMENT = "There is no element with name %s!";
	private int nextId;

	private final List<Team> teams = new ArrayList<>();
	private final List<User> users = new ArrayList<>();

	//TODO discuss should we include boards in repository or just to access them thru teams (name of a board to be unique in a team, not application)
	private final List<Board> boards = new ArrayList<>();

	//TODO discuss if we should include all tasks here, or should we access them thru teams -> boards (or thru boards directly)
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
		this.tasks.add(bug);
		return bug;
	}

	@Override
	public Story createStory(String title, String description, PriorityType priority, SizeType size) {
		Story story = new StoryImpl(++nextId, title, description, priority, size);
		this.tasks.add(story);
		return story;
	}

	@Override
	public Feedback createFeedback(String title, String description, int rating) {
		Feedback feedback = new FeedbackImpl(++nextId, title, description, rating);
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
				.orElseThrow(() -> new ElementNotFoundException(String.format(NO_RECORD_ID, id)));
	}

	//TODO discuss should we add some common interface (to Board, Team, User) to make this method generic, as we will have to search for user, team (and board name);
	public User findUserByName(String name) {
		return users.stream()
				.filter(u -> u.getName().equalsIgnoreCase(name))
				.findFirst()
				.orElseThrow(() -> new ElementNotFoundException(String.format(NO_SUCH_ELEMENT, name)));
	}

	public Team findTeamByName(String name) {
		return teams.stream()
				.filter(u -> u.getName().equalsIgnoreCase(name))
				.findFirst()
				.orElseThrow(() -> new ElementNotFoundException(String.format(NO_SUCH_ELEMENT, name)));
	}

	public Board findBoardByName(String name) {
		/**
		 *  Nested search -> search boards in each team
		 */
		return teams.stream()
				.flatMap(team -> team.getBoards().stream())
				.filter(board -> board.getName().equalsIgnoreCase(name))
				.findFirst()
				.orElseThrow(() -> new ElementNotFoundException(String.format(NO_SUCH_ELEMENT, name)));
	}
}
