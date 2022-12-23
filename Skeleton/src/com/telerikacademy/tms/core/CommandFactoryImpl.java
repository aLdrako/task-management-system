package com.telerikacademy.tms.core;

import com.telerikacademy.tms.commands.*;
import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.commands.enums.CommandType;
import com.telerikacademy.tms.core.contracts.CommandFactory;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;

import static com.telerikacademy.tms.utils.ParsingHelpers.tryParseEnum;

public class CommandFactoryImpl implements CommandFactory {

	public Command createCommandFromCommandName(String commandName, TaskManagementRepository repository) {
		CommandType commandType = tryParseEnum(commandName, CommandType.class);
		switch (commandType) {
			case CREATEPERSON:
				return new CreatePerson(repository);
			case SHOWALLPEOPLE:
				return new ShowAllPeople(repository);
			case SHOWPERSONACTIVITY:
				return new ShowPersonActivity(repository);
			case CREATETEAM:
				return new CreateTeam(repository);
			case SHOWALLTEAMS:
				return new ShowAllTeams(repository);
			case SHOWTEAMACTIVITY:
				return new ShowTeamActivity(repository);
			case ADDPERSONTOTEAM:
				return new AddPersonToTeam(repository);
			case SHOWALLTEAMMEMBERS:
				return new ShowAllTeamMembers(repository);
			case CREATEBOARDINTEAM:
				return new CreateBoardInTeam(repository);
			case SHOWALLTEAMBOARDS:
				return new ShowAllTeamBoards(repository);
			case SHOWBOARDACTIVITY:
				return new ShowBoardActivity(repository);
			case CREATETASKINBOARD:
				return new CreateTaskInBoard(repository);
			case ADDSTEPSTOBUG:
				return new AddStepsToBug(repository);
			case CHANGESTORY:
				return new ChangeStory(repository);
			case CHANGEBUG:
				return new ChangeBug(repository);
			case CHANGEFEEDBACK:
				return new ChangeFeedback(repository);
			case ASSIGNTASK:
				return new AssignTask(repository);
			case UNASSIGNTASK:
				return new UnassignTask(repository);
			case ADDCOMMENT:
				return new AddComment(repository);
			case LISTALLTASKS:
				return new ListAllTasks(repository);
			case LISTALLBUGS:
				return new ListAllBugs(repository);
			case LISTALLSTORIES:
				return new ListAllStories(repository);
			case LISTALLFEEDBACKS:
				return new ListAllFeedbacks(repository);
			case LISTTASKSWITHASSIGNEE:
				return new ListTasksWithAssignee(repository);
			default:
				throw new IllegalArgumentException();
		}
	}
}
