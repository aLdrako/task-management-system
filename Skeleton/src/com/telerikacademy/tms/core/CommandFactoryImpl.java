package com.telerikacademy.tms.core;

import com.telerikacademy.tms.commands.board.CreateBoardInTeamCommand;
import com.telerikacademy.tms.commands.board.ShowBoardActivityCommand;
import com.telerikacademy.tms.commands.bugs.*;
import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.commands.enums.CommandType;
import com.telerikacademy.tms.commands.feedback.ChangeFeedbackRatingCommand;
import com.telerikacademy.tms.commands.feedback.ChangeFeedbackStatusCommand;
import com.telerikacademy.tms.commands.feedback.CreateFeedbackCommand;
import com.telerikacademy.tms.commands.feedback.ListFeedbackCommand;
import com.telerikacademy.tms.commands.person.AddPersonToTeamCommand;
import com.telerikacademy.tms.commands.person.CreatePersonCommand;
import com.telerikacademy.tms.commands.person.ShowAllPeopleCommand;
import com.telerikacademy.tms.commands.person.ShowPersonActivityCommand;
import com.telerikacademy.tms.commands.story.*;
import com.telerikacademy.tms.commands.task.*;
import com.telerikacademy.tms.commands.team.*;
import com.telerikacademy.tms.core.contracts.CommandFactory;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;

public class CommandFactoryImpl implements CommandFactory {
    private static final String INVALID_COMMAND = "Invalid command name: %s!";

    public Command createCommandFromCommandName(String commandName, TaskManagementRepository repository) {
        CommandType commandType = CommandType.valueOf(commandName.toUpperCase());
        switch (commandType){
            case CREATEPERSON:
                return new CreatePersonCommand(repository);
            case SHOWALLPEOPLE:
                return new ShowAllPeopleCommand(repository);
            case SHOWPERSONACTIVITY:
                return new ShowPersonActivityCommand(repository);
            case CREATETEAM:
                return new CreateTeamCommand(repository);
            case SHOWALLTEAMS:
                return new ShowAllTeamsCommand(repository);
            case SHOWTEAMACTIVITY:
                return new ShowAllTeamActivity(repository);
            case ADDPERSONTOTEAM:
                return new AddPersonToTeamCommand(repository);
            case SHOWALLTEAMMEMBERS:
                return new ShowAllTeamMembersCommand(repository);
            case CREATENEWBOARDINTEAM:
                return new CreateBoardInTeamCommand(repository);
            case SHOWALLTEAMBOARDS:
                return new ShowAllTeamBoardsCommand(repository);
            case SHOWBOARDACTIVITY:
                return new ShowBoardActivityCommand(repository);
            case CREATEBUGINBOARD:
                return new CreateBugCommand(repository);
            case CREATESTORYINBOARD:
                return new CreateStoryCommand(repository);
            case CREATEFEEDBACKINBOARD:
                return new CreateFeedbackCommand(repository);
            case CHANGEPRIORITYOFBUG:
                return new ChangeBugPriorityCommand(repository);
            case CHANGESEVERITY:
                return new ChangeBugSeverityCommand(repository);
            case CHANGEBUGSTATUS:
                return new ChangeBugStatusCommand(repository);
            case CHANGESTORYPRIORITY:
                return new ChangeStoryPriorityCommand(repository);
            case CHANGESTORYSIZE:
                return new ChangeStorySizeCommand(repository);
            case CHANGESTORYSTATUS:
                return new ChangeStoryStatusCommand(repository);
            case CHANGERATING:
                return new ChangeFeedbackRatingCommand(repository);
            case CHANGEFEEDBACKSTATUS:
                return new ChangeFeedbackStatusCommand(repository);
            case ADDSTEPSTOREPRODUCEBUG:
                return new AddStepsToReproduceBugCommand(repository);
            case ASSIGNTASK:
                return new AssignTaskToPersonCommand(repository);
            case UNASSIGNTASK:
                return new UnassignTaskFromPersonCommand(repository);
            case ADDCOMMENTTOTASK:
                return new AddCommentToTaskCommand(repository);
            case LISTALLTASKS:
                return new ListAllTasksCommand(repository);
            case LISTBUGS:
                return new ListBugsCommand(repository);
            case LISTSTORIES:
                return new ListStoriesCommand(repository);
            case LISTFEEDBACK:
                return new ListFeedbackCommand(repository);
            case LISTASSIGNEETASKS:
                return new ListTasksWithAssignee(repository);
            default:
                throw new IllegalArgumentException(String.format(INVALID_COMMAND, commandName));
        }
    }

}
