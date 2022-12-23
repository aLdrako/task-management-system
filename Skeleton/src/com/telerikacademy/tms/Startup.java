package com.telerikacademy.tms;

import com.telerikacademy.tms.core.TaskManagementEngineImpl;
import com.telerikacademy.tms.core.contracts.Engine;
import com.telerikacademy.tms.models.BoardImpl;
import com.telerikacademy.tms.models.TeamImpl;
import com.telerikacademy.tms.models.UserImpl;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.contracts.User;
import com.telerikacademy.tms.models.tasks.BugImpl;
import com.telerikacademy.tms.models.tasks.StoryImpl;
import com.telerikacademy.tms.models.tasks.contracts.Task;
import com.telerikacademy.tms.models.tasks.enums.PriorityType;
import com.telerikacademy.tms.models.tasks.enums.SeverityType;
import com.telerikacademy.tms.models.tasks.enums.SizeType;
import com.telerikacademy.tms.models.tasks.enums.StoryStatus;

public class Startup {
	public static void main(String[] args) {

		Engine engine = new TaskManagementEngineImpl();
		engine.start();

//		Team team = new TeamImpl("Team 12");
//
//	    User user1 = new UserImpl("Argir");
//	    User user2 = new UserImpl("Samuil");
//	    User user3 = new UserImpl("Alexander");
//
//	    Board board1 = new BoardImpl("BoardOne");
//	    Board board2 = new BoardImpl("BoardTwo");
//
//	    Task task1 = new BugImpl(1, "Models to implement", "Defines model of the project", PriorityType.MEDIUM, SeverityType.MINOR);
//	    Task task2 = new StoryImpl(2, "Some good story", "Enjoy coding, while...", PriorityType.LOW, SizeType.MEDIUM);
//	    Task task3 = new BugImpl(3, "Commands to implement", "Defines commands of the project", PriorityType.LOW, SeverityType.CRITICAL);
//
//	    team.addUser(user1);
//	    team.addUser(user2);
//	    team.addUser(user3);
//
//	    team.addBoard(board1);
//
//	    board1.addTask(task1);
//	    board1.addTask(task2);
//
//	    user1.assignTask(task2);
//	    user1.assignTask(task1);
//	    user1.unAssignTask(task1);
//	    user1.assignTask(task3);
//
//	    task2.setStatus(StoryStatus.DONE);
//
//	    System.out.println(team);
//	    System.out.println();
//	    System.out.println(user1);
//	    System.out.println();
//	    System.out.println(board1);


	}
}