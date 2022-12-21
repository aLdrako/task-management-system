package com.telerikacademy.tms;

import com.telerikacademy.tms.core.TaskManagementEngineImpl;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.Engine;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.BoardImpl;
import com.telerikacademy.tms.models.TeamImpl;
import com.telerikacademy.tms.models.UserImpl;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.BugImpl;
import com.telerikacademy.tms.models.tasks.StoryImpl;
import com.telerikacademy.tms.models.tasks.contracts.Task;
import com.telerikacademy.tms.models.tasks.enums.StoryStatus;

public class Startup {
	public static void main(String[] args) {
	//Team team = new TeamImpl("Team 12");

	//User user1 = new UserImpl("Argir");
	//User user2 = new UserImpl("Samuil");
	//User user3 = new UserImpl("Alexander");

	//Board board1 = new BoardImpl("BoardOne");
	//Board board2 = new BoardImpl("BoardTwo");

	//Task task1 = new BugImpl(1, "Models to implement", "Defines model of the project");
	//Task task2 = new StoryImpl(2, "Some good story", "Enjoy coding, while...");
	//Task task3 = new BugImpl(3, "Commands to implement", "Defines commands of the project");

	//team.addUser(user1);
	//team.addUser(user2);
	//team.addUser(user3);

	//team.addBoard(board1);

	//board1.addTask(task1);
	//board1.addTask(task2);

	//user1.assignTask(task2);
	//user1.assignTask(task1);
	//user1.unAssignTask(task1);
	//user1.assignTask(task3);

	//task2.setStatus(StoryStatus.DONE);

	//System.out.println(team);
	//System.out.println();
	//System.out.println(user1);
	//System.out.println(board1);

	//System.out.println(task3.getStatus());

//		TaskManagementRepository taskManagementRepository = new TaskManagementRepositoryImpl();
//		Team team = taskManagementRepository.createTeam("DevOps");
//		Team team2 = taskManagementRepository.createTeam("Mortals");
//
//		Board board = taskManagementRepository.createBoard("Board1");
//		Board board2 = taskManagementRepository.createBoard("Board2");
//
//		User user = taskManagementRepository.createUser("Alexander");
//		team.addBoard(board2);
//		team2.addBoard(board2);
//
//		var result = taskManagementRepository.findElementByName(taskManagementRepository.getTeams(), "Mortals");
//		var result2 = taskManagementRepository.findBoardByNameInTeam(team, "Board2");
//
//		System.out.println(result);

		Engine engine = new TaskManagementEngineImpl();
		engine.start();

	}
}
