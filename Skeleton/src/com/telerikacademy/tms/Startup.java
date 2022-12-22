package com.telerikacademy.tms;

import com.telerikacademy.tms.core.TaskManagementEngineImpl;
import com.telerikacademy.tms.core.contracts.Engine;

public class Startup {
	public static void main(String[] args) {

		Engine engine = new TaskManagementEngineImpl();
		engine.start();

	//	StoryImpl story = new StoryImpl(5, "dsdsadasdadasd", "dsadasdasdgd", PriorityType.HIGH,
	//			SizeType.LARGE);
	//	story.setStatus(StoryStatus.INPROGRESS);
	//	System.out.println(story.getHistories());


//	    Team team = new TeamImpl("Team 12");
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
//	    System.out.println(board1);//
//	    System.out.println(task3.getStatus());

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
//		System.out.println(result);

//		taskManagementRepository.createFeedback("Good Feedback", "Some good feedback here", Rating.NINE);
//
//		Command command = new ChangeFeedback(taskManagementRepository);
//		String output = command.execute(List.of("1", "rating", "6"));
//		String output2 = command.execute(List.of("1", "rating", "6"));
//
//		System.out.println(output);
//		System.out.println(output2);

	}
}
