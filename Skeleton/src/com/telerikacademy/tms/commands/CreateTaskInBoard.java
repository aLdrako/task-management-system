package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.contracts.Board;
import com.telerikacademy.tms.models.contracts.Team;
import com.telerikacademy.tms.models.tasks.contracts.Bug;
import com.telerikacademy.tms.models.tasks.contracts.Feedback;
import com.telerikacademy.tms.models.tasks.contracts.Story;
import com.telerikacademy.tms.models.tasks.enums.*;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.tms.utils.ParsingHelpers.convertDigitToWord;
import static com.telerikacademy.tms.utils.ParsingHelpers.tryParseEnum;
import static com.telerikacademy.tms.utils.ValidationHelpers.validateArgumentsMin;
import static com.telerikacademy.tms.utils.ValidationHelpers.validateInRange;
import static java.lang.String.format;

public class CreateTaskInBoard implements Command {
    private static final int EXPECTED_MIN_TASK_PARAMETERS = 6;
    private static final int EXPECTED_MIN_BUG_PARAMETERS = 8;
    private static final int EXPECTED_MIN_STORY_PARAMETERS = 7;
    public static final String TASK_CREATED_SUCCESSFULLY = "%s <%s> with ID -> [%d] has been created in board <%s>!";
    private static final int BUG_STEPS_MIN_LEN = 5;
    private static final int BUG_STEPS_MAX_LEN = 100;
    private static final String BUG_STEPS_LEN_ERR = format(
            "Bug single step must be between %d and %d characters long!",
            BUG_STEPS_MIN_LEN,
            BUG_STEPS_MAX_LEN);
    private final TaskManagementRepository repository;

    public CreateTaskInBoard(TaskManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateArgumentsMin(parameters, EXPECTED_MIN_TASK_PARAMETERS);//Min task is same as min feedback.
        TaskType taskType = tryParseEnum(parameters.get(0), TaskType.class);
        String boardName = parameters.get(1);
        String teamName = parameters.get(2);
        Team team = repository.findElementByName(repository.getTeams(), teamName);
        Board board = repository.findBoardByNameInTeam(team, boardName);
        String title = parameters.get(3);
        String description = parameters.get(4);
        int id = 0;
        switch (taskType) {
            case BUG: {
                validateArgumentsMin(parameters, EXPECTED_MIN_BUG_PARAMETERS);
                PriorityType priority = tryParseEnum(parameters.get(5), PriorityType.class);
                SeverityType severity = tryParseEnum(parameters.get(6), SeverityType.class);
                List<String> stepsToReproduce = new ArrayList<>();
                for (int i = 7; i < parameters.size(); i++) {
                    validateInRange(parameters.get(i).strip().length(), BUG_STEPS_MIN_LEN, BUG_STEPS_MAX_LEN, BUG_STEPS_LEN_ERR);
                    stepsToReproduce.add(format("%s: %s", i - 6, parameters.get(i).strip()));
                }
                Bug bug = repository.createBug(title, description, priority, severity, stepsToReproduce);
                id = bug.getID();
                board.addTask(bug);
                break;
            }
            case STORY: {
                validateArgumentsMin(parameters, EXPECTED_MIN_STORY_PARAMETERS);
                PriorityType priority = tryParseEnum(parameters.get(5), PriorityType.class);
                SizeType size = tryParseEnum(parameters.get(6), SizeType.class);
                Story story = repository.createStory(title, description, priority, size);
                id = story.getID();
                board.addTask(story);
                break;
            }
            case FEEDBACK: {
                Rating rating = tryParseEnum(convertDigitToWord(parameters.get(5)), Rating.class);
                Feedback feedback = repository.createFeedback(title, description, rating);
                id = feedback.getID();
                board.addTask(feedback);
                break;
            }
        }
        return format(TASK_CREATED_SUCCESSFULLY, taskType, title, id, board.getName());
    }
}
