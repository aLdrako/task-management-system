<img src="https://webassets.telerikacademy.com/images/default-source/logos/telerik-academy.svg" alt="logo" width="300px" style="margin-top: 20px;"/>

# Task Management System

## Project Description
Design and implement a **Tasks Management** console application.

The application will be used by a small team of developers, who need to keep track of all the tasks, surrounding a software product they are building.
## Functional Requirements
The application ***must*** support multiple **teams**.
Each team ***must*** have a **name**, **members**, and **boards**.
- The name ***must*** be unique in the application.
- The name is a string between 5 and 15 symbols.

Each member ***must*** have a **name**, list of **tasks** and **activity history**.
- The name ***must*** be unique in the application.
- The name is a string between 5 and 15 symbols.

Each board ***must*** have a **name**, list of **tasks** and **activity history**.
- Name ***must*** be unique in the team.
 - Name is a string between 5 and 10 symbols

There are 3 types of tasks: **bug**, **story**, and **feedback**.

### Bug
Bugs ***must*** have an ID, a title, a description, a list of steps to reproduce it, a priority, a severity, a status, an assignee, a list of comments and a list of changes history.
- Title is a string between 10 and 50 symbols.
- Description is a string between 10 and 500 symbols.
- Steps to reproduce is a list of strings.
- Priority is one of the following: **High**, **Medium**, or **Low**.
- Severity is one of the following: **Critical**, **Major**, or **Minor**.
- Status is one of the following: **Active** or **Fixed**.
- Assignee is a member from the team.
- Comments is a list of comments (string messages with an author).
- History is a list of all changes (string messages) that were done to the bug.

### Story
Stories ***must*** have an ID, a title, a description, a priority, a size, a status, an assignee, a list of comments and a list of changes history.
- Title is a string between 10 and 50 symbols.
- Description is a string between 10 and 500 symbols.
- Priority is one of the following: **High**, **Medium**, or **Low**.
- Size is one of the following: **Large**, **Medium**, or **Small**.
- Status is one of the following: **Not Done**, **InProgress**, or **Done**.
- Assignee is a member from the team.
- Comments is a list of comments (string messages with author).
- History is a list of all changes (string messages) that were done to the story.

### Feedback
Feedbacks ***must*** have an ID, a title, a description, a rating, a status, a list of comments and a list of changes history.
- Title is a string between 10 and 50 symbols.
- Description is a string between 10 and 500 symbols.
- Rating is an integer.
- Status is one of the following: **New**, **Unscheduled**, **Scheduled**, or **Done**.
- Comments is a list of comments (string messages with author).
- History is a list of all changes (string messages) that were done to the feedback.

**Note: IDs of tasks *must* be unique in the application i.e., if we have a bug with ID 42 then we cannot have a story or a feedback with ID 42.**

## Operations
The application ***must*** support the following operations:
- Create a new person.
- Show all people.
- Show person's activity.
- Create a new team.
- Show all teams.
- Show team's activity.
- Add person to team.
- Show all team members.
- Create a new board in a team.
- Show all team boards.
- Show board's activity.
- Create a new Bug/Story/Feedback in a board.
- Change the Priority/Severity/Status of a bug.
- Change the Priority/Size/Status of a story.
- Change the Rating/Status of a feedback.
- Assign/Unassign a task to a person.
- Add comment to a task.
- Listing
  - List all tasks (display the most important info).
    - Filter by title
    - Sort by title
  - List bugs/stories/feedback only.
    - Filter by status and/or assignee
    - Sort by title/priority/severity/size/rating (depending on the task type)
  - List tasks with assignee.
    - Filter by status and/or assignee
    - Sort by title

## Use cases
#### Use case №1
One of the developers has noticed a bug in the company’s product. He starts the application and goes on to create a new Task for it. He creates a new Bug and gives it the title "The program freezes when the Log In button is clicked." For the description he adds "This needs to be fixed quickly!", he marks the Bug as High priority and gives it Critical severity. Since it is a new bug, it gets the Active status. The developer also assigns it to the senior developer in the team. To be able to fix the bug, the senior developer needs to know how to reproduce it, so the developer who logged the bug adds a list of steps to reproduce: "1. Open the application; 2. Click "Log In"; 3. The application freezes!" The bug is saved to the application and is ready to be fixed.
#### Use case №2
A new developer has joined the team. One of the other developers starts the application and creates a new team member. After that, he adds the new team member to one of the existing teams and assigns all Critical bugs to him.
#### Use case №3
One of the developers has fixed a bug that was assigned to him. He adds a comment to that bug, saying "This one took me a while, but it is fixed now!", and then changes the status of the bug to Fixed. Just to be sure, he checks the changes history list of the bug and sees that the last entry in the list says, "The status of item with ID 42 switched from Active to Fixed."

## Technical Requirements
- Follow the **OOP best practices**:
 - Use data encapsulation.
 - Use inheritance and polymorphism properly.
 - Use interfaces and abstract classes properly.
 - Use static members properly.
 - Use enumerations properly.
 - Aim for strong cohesion and loose coupling.
- Follow guidelines for writing **clean code**:
 - Proper naming of classes, methods, and fields.
 - Small classes and methods.
 - Well formatted and consistent code.
 - No duplicate code.
- Implement proper user input **validation** and display meaningful user messages.
- Implement proper **exception handling**.
- Prefer using the Streaming API, then using ordinary loops, wherever you can.
- Cover the core functionality with unit tests (**at least 80%** code coverage of the models and commands).
 - There is no need to test the printing commands.
- Use **Git** to keep your source code and for team collaboration.

## Implemented Commands

- CreatePerson `{String name}`
- ShowAllPeople
- ShowPersonActivity `{String name}`
- CreateTeam `{String teamName}`
- ShowAllTeams
- ShowTeamActivity `{String teamName}`
- AddPersonToTeam `{String userName} {String teamName}`
- ShowAllTeamMembers `{String teamName}`
- ShowAllTeamBoards `{String teamName}`
- CreateBoardInTeam `{String boardName} {String teamName}`
- ShowBoardActivity `{String boardName}`
- CreateTaskInBoard `[enum]{bug/story/feedback} {(coresponding task parameters)}`
- AddStepsToBug `{ind id} {String steps} {String steps} ...`
- AssignTask `{ind id} {String name}`
- UnassignTask `{ind id} {String name}`
- AddComment `{ind id} {String comment} {String name}`
- ChangeStory `{ind id} [switch]{status/priority/severity} [enum]`
- ChangeBug `{ind id} [switch]{status/priority/severity} [enum]`
- ChangeFeedback `{ind id} [switch]{status/rating} [enum]`
- ListAllTasks
- ListAllBugs
- ListAllStories
- ListAllFeedbacks
- ListTasksWithAssignee

## Teamwork Guidelines
Please see the Teamwork Guidelines [document](https://learn.telerikacademy.com/mod/page/view.php?id=38822 "document").

### Sample Input
```none
Command
CreatePerson
CreatePerson Alexa
CreatePerson Alexa
CreatePerson Cortana
CreateTeam Cortana
CreateTeam Hackers
CreateTeam Developers
CreateTeam {{Suicide Squad}}
AddPersonToTeam Alexa {{Suicide Squad}}
AddPersonToTeam Cortana {{Suicide Squad}}
ShowAllTeams
ShowAllPeople
ShowTeamActivity Hackers
ShowTeamActivity {{Suicide Squad}}
ShowPersonActivity Cortana
ShowAllTeamMembers {{Suicide Squad}}
ShowAllTeamBoards Hackers
CreateBoardInTeam ToDos Hackers
CreateBoardInTeam Goals {{Suicide Squad}}
ShowBoardActivity ToDos
CreateTaskInBoard bug ToDos {{Glitching inteface}} {{There is a broken interface}} high major
CreateTaskInBoard STORY ToDos {{Funny story}} {{Once upon a time there was a mouse....}} medium small
CreateTaskInBoard feedback Goals {{Good job here}} {{This developer is doing great}} 9
AddStepsToBug 1 {{First open the app}} {{Try to access the database}}
AddStepsToBug 2 {{First open the app}} {{Try to access the database}}
AssignTask 1 Alexa
AssignTask 2 Alexa
AssignTask 3 Cortana
UnassignTask 1 Ivan
UnassignTask 12 Cortana
UnassignTask 2 Alexa
AddComment 3 {{Thank you so much for the amazing feedback}} Cortana
ChangeStory 2 status InProgress
ChangeStory 2 priority Low
ChangeBug 1 severity Major
ChangeBug 1 severity Minor
ChangeFeedback 1 rating 9
ChangeFeedback 3 rating 10

```

### Sample Output
```none
There is no value (Command) in CommandType.
Invalid number of arguments. Expected: 1; received: 0.
User with a name Alexa was created.
Duplicate name. Please enter a unique name!
User with a name Cortana was created.
Duplicate name. Please enter a unique name!
Team with a name Hackers was created.
Team with a name Developers was created.
Team with a name Suicide Squad was created.
Person Alexa has been added to the team Team: Suicide Squad
It has (1) users -> Alexa
It contains (0) boards -> !
Person Cortana has been added to the team Team: Suicide Squad
It has (2) users -> Alexa, Cortana
It contains (0) boards -> !
=== ALL TEAMS ===
Team: Hackers
It has (0) users -> 
It contains (0) boards -> 
===============
Team: Developers
It has (0) users -> 
It contains (0) boards -> 
===============
Team: Suicide Squad
It has (2) users -> Alexa, Cortana
It contains (0) boards -> 

=== ALL PEOPLE ===
User: Alexa has (0) assigned tasks
<<< Alexa's Activity History >>>
[23-December-2022 20:50:25] User was created.
===============
User: Cortana has (0) assigned tasks
<<< Cortana's Activity History >>>
[23-December-2022 20:50:25] User was created.

Hackers's team activity:
[23-December-2022 20:50:25] Team was created.

Suicide Squad's team activity:
[23-December-2022 20:50:25] Team was created.
[23-December-2022 20:50:25] User Alexa added to the team Suicide Squad
[23-December-2022 20:50:25] User Cortana added to the team Suicide Squad

Cortana's activity:
[23-December-2022 20:50:25] User was created.

Suicide Squad' team members: Alexa, Cortana 
Team Hackers boards printed
Board ToDos has been created in team Hackers!
Board Goals has been created in team Suicide Squad!
ToDos's activity:
[23-December-2022 20:50:25] Board was created.

Task Glitching inteface has been created in board ToDos!
Task Funny story has been created in board ToDos!
Task Good job here has been created in board Goals!
Steps to reproduce added to Bug with ID 1
Provided task with ID 2 does not belong to Bug category!
Task with ID 1 was assigned to user Alexa.
Task with ID 2 was assigned to user Alexa.
Provided task with ID 3 does not belong to Assignable category!
There is no User or Team with name Ivan!
No task with ID 12
Task with ID 2 was unassigned from user Alexa.
User Cortana added comment to task with ID 3
Status for Story with ID 2 was changed to InProgress.
Priority for Story with ID 2 was changed to Low.
Same parameter passed. Nothing to changed.
Severity for Bug with ID 1 was changed to Minor.
Provided task with ID 1 does not belong to Feedback category!
Rating for Feedback with ID 3 was changed to 10.

```