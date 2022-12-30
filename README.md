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
- ShowAllPeople
- ShowAllTeams
- CreatePerson `{String name}`
- ShowPersonActivity `{String name}`
- CreateTeam `{String teamName}`
- ShowTeamActivity `{String teamName}`
- AddPersonToTeam `{String userName} {String teamName}`
- ShowAllTeamMembers `{String teamName}`
- ShowAllTeamBoards `{String teamName}`
- CreateBoardInTeam `{String boardName} {String teamName}`
- ShowBoardActivity `{String boardName} {String teamName}`
- CreateTaskInBoard `[bug/story/feedback] {String boardName} {String teamName} {(coresponding task parameters)}`
- AssignTask `{ind id} {String name}`
- UnassignTask `{ind id}`
- AddComment `{ind id} {String comment} {String name}`
- ChangeBug `{ind id} [status/priority/severity/] {String (enums)}`
- ChangeStory `{ind id} [status/priority/size] {String (enums)}`
- ChangeFeedback `{ind id} [status/rating] {String (enums)}`
- ShowTaskActivity `{ind id}`
- ListAllTasks `sortByTitle || filterByTitle {String title} || filterByTitle {String title} sortByTitle` 
- ListAllBugs `[sortByTitle/sortByPriority/sortBySeverity] || [filterByStatus/filterByAssignee/filterByStatusAndAssignee] {String [status/assignee/status assignee]} || [filterByStatus/filterByAssignee/filterByStatusAndAssignee] {String [status/assignee/status assignee]} [sortByTitle/sortByPriority/sortBySeverity]` 
- ListAllStories `[sortByTitle/sortByPriority/sortBySize] || [filterByStatus/filterByAssignee/filterByStatusAndAssignee] {String [status/assignee/status assignee]} || [filterByStatus/filterByAssignee/filterByStatusAndAssignee] {String [status/assignee/status assignee]} [sortByTitle/sortByPriority/sortBySize]`
- ListAllFeedbacks `[sortByTitle/sortByRating] || filterByStatus {String status} || filterByStatus {String status} [sortByTitle/sortByPriority]`
- ListTasksWithAssignee `sortByTitle || [filterByAssignee/filterByStatus] {String [userName/status]} || [filterByAssignee/filterByStatus] {String [userName/status]} sortByTitle || filterByAssignee {String userName} filterByStatus {String status} || filterByAssignee {String userName} filterByStatus {String status} sortByTitle`

## Teamwork Guidelines
Please see the Teamwork Guidelines [document](https://learn.telerikacademy.com/mod/page/view.php?id=38822 "document").

### Sample Input
```none
Command
CreatePerson
CreatePerson Alexa
CreatePerson Alexa
CreatePerson Cortana
CreatePerson {{Marco Polo}}
CreatePerson Jimmy
CreateTeam Cortana
CreateTeam Hackers
CreateTeam Developers
CreateTeam {{Suicide Squad}}
CreateBoardInTeam ToDos Hackers
CreateBoardInTeam ToDos {{Suicide Squad}}
CreateBoardInTeam Goals {{Suicide Squad}}
AddPersonToTeam Alexa {{Suicide Squad}}
AddPersonToTeam Cortana {{Suicide Squad}}
AddPersonToTeam Cortana {{Suicide Squad}}
AddPersonToTeam Jimmy Hackers
ShowBoardActivity Todos Hackers
ShowBoardActivity Todos {{Suicide Squad}}
ShowPersonActivity Alexa
ShowPersonActivity {{Marco Polo}}
ShowPersonActivity Cortana 
ShowAllTeams
ShowAllPeople
AddComment 1 {{I like how you did this}} {{Marco Polo}}
CreateTaskInBoard bug ToDos Hackers {{Glitching interface}} {{There is a broken interface}} low major {{ You have to open the interface }} {{You will understand what I mean }}
CreateTaskInBoard bug ToDos Hackers {{Serious issue}} {{App does not work}} medium major {{Click register user button}} {{Type your email}} {{App crashes!}} {{Fix it}}
CreateTaskInBoard bug ToDos Hackers {{Fatal issue}} {{Server is down}} high critical {{Open that }} {{Do this}}
ChangeBug 2 Status Fixed
ChangeFeedback 1 
CreateTaskInBoard bug ToDos {{Suicide Squad}} {{Serious issue}} {{App does not work}} medium large {{Fatal error}} {{Panic mode}} 
CreateTaskInBoard bug Ultimate Hackers {{Serious issue}} {{App does not work}} medium large {{Open that}} {{Do this}}
CreateTaskInBoard bug ToDos Hackers {{Serious issue}} {{App does not work}} medium large
CreateTaskInBoard story Goals {{Suicide Squad}} {{Another story}} {{Once upon a time there was a cat....}} medium small
CreateTaskInBoard story ToDos Hackers {{Funny story}} {{Once upon a time there was a mouse....}} high medium
CreateTaskInBoard story ToDos {{Suicide Squad}}{{Third story}} {{Once upon a time there was a dog....}} low large
CreateTaskInBoard feedback ToDos Hackers {{Great job here}} {{This developer is doing great}} 9
CreateTaskInBoard feedback Goals {{Suicide Squad}} {{Doing good}} {{There is still room for improvement}} 7
CreateTaskInBoard feedback Goals {{Suicide Squad}} {{An Impressive job}} {{Thats what I call excellent work}} 10
ChangeFeedback 8 Status Unscheduled
ChangeFeedback 8 Status Unscheduled
ChangeFeedback 9 Status Unscheduled
ChangeStory 6 Size Small
ChangeStory 8 Size Small
ChangeFeedback 8 rating 9
CreateTaskInBoard feedback Goals {{Suicide Squad}} {{An Impressive job}} {{Thats what I call excellent work}} 11
assigntask 1 Alexa
assigntask 2 Alexa
assigntask 3 Alexa
assigntask 4 Alexa
assigntask 5 Alexa
assigntask 4 Cortana
assigntask 6 Cortana
assigntask 6 Alexa
assigntask 1 jimmy
assigntask 2 jimmy
assigntask 3 jimmy
assigntask 9 jimmy
showpersonactivity alexa
showboardactivity Todos Hackers
showboardactivity Todos {{Suicide Squad}}
showtaskactivity 2
addcomment 1 {{I like how you did this}} {{Marco Polo}}
addcomment 1 {{Perfect job here}} {{Marco Polo}}
showtaskactivity 1
unassigntask 1
unassigntask 5
unassigntask 7
ShowAllTeams
ShowAllPeople
showtaskactivity 1
showteamactivity Hackers
showpersonactivity jimmy
ListTasksWithAssignee
ListTasksWithAssignee filterByAssignee Jimmy filterByStatus Fixed sortByTitle
ListAllFeedbacks sortByRating
ListAllFeedbacks filterByStatus New sortByRating
ListAllBugs sortBySeverity
ListAllBugs filterByStatus Active sortByTitle
ListAllBugs filterByAssignee Alexa sortByPriority
ListAllStories sortBySize
ListAllStories filterByStatus InProgress sortByTitle
ListAllStories filterByStatusAndAssignee InProgress Cortana sortByTitle
ListAllFeedbacks filterByStatus Unscheduled sortByRating
ListAllTasks 
ListAllTasks filterByTitle {{Great job}}
ListAllTasks filterByTitle story sortByTitle
ListAllBugs filterByStatusAndAssignee Fixed Jimmy sortByPriority

```
### Sample Output
```none
There is no value (Command) in CommandType.
Invalid number of arguments. Expected: 1; received: 0.
User with a name <Alexa> was created.
Duplicate name. Please enter a unique name!
User with a name <Cortana> was created.
User with a name <Marco Polo> was created.
User with a name <Jimmy> was created.
Duplicate name. Please enter a unique name!
Team with a name <Hackers> was created.
Team with a name <Developers> was created.
Team with a name <Suicide Squad> was created.
Board <ToDos> has been created in team <Hackers>!
Board <ToDos> has been created in team <Suicide Squad>!
Board <Goals> has been created in team <Suicide Squad>!
Person <Alexa> has been added to the team <Suicide Squad>!
Person <Cortana> has been added to the team <Suicide Squad>!
User <Cortana> already in team <Suicide Squad>
Person <Jimmy> has been added to the team <Hackers>!
<<< <Hackers> board's ToDos ACTIVITY HISTORY >>>
[30-December-2022 21:11:40] Board was created.
<<< <Suicide Squad> board's ToDos ACTIVITY HISTORY >>>
[30-December-2022 21:11:40] Board was created.
<<< Alexa User's ACTIVITY HISTORY >>>
[30-December-2022 21:11:40] User was created.
[30-December-2022 21:11:40] User was added to team <Suicide Squad>
<<< Marco Polo User's ACTIVITY HISTORY >>>
[30-December-2022 21:11:40] User was created.
<<< Cortana User's ACTIVITY HISTORY >>>
[30-December-2022 21:11:40] User was created.
[30-December-2022 21:11:40] User was added to team <Suicide Squad>
=== ALL TEAMS ===
Team: Hackers
It has (1) users -> Jimmy
It contains (1) boards -> ToDos
===============
Team: Developers
It has (0) users
It contains (0) boards
===============
Team: Suicide Squad
It has (2) users -> Alexa, Cortana
It contains (2) boards -> ToDos, Goals
=== ALL PEOPLE ===
User: Alexa has (0) assigned tasks
===============
User: Cortana has (0) assigned tasks
===============
User: Marco Polo has (0) assigned tasks
===============
User: Jimmy has (0) assigned tasks
No task with ID -> 1
Bug <Glitching interface> with ID -> [1] has been created in board <ToDos>!
Bug <Serious issue> with ID -> [2] has been created in board <ToDos>!
Bug <Fatal issue> with ID -> [3] has been created in board <ToDos>!
Status for Bug with ID -> [2] was changed to {Fixed}.
Invalid number of arguments. Expected: 3; received: 1.
There is no value (large) in SeverityType.
There is no Board with name 'Ultimate' in Team 'Hackers'!
Invalid number of arguments. Expected: at least 8; received: 7.
Story <Another story> with ID -> [4] has been created in board <Goals>!
Story <Funny story> with ID -> [5] has been created in board <ToDos>!
Story <Third story> with ID -> [6] has been created in board <ToDos>!
Feedback <Great job here> with ID -> [7] has been created in board <ToDos>!
Feedback <Doing good> with ID -> [8] has been created in board <Goals>!
Feedback <An Impressive job> with ID -> [9] has been created in board <Goals>!
Status for Feedback with ID -> [8] was changed to {Unscheduled}.
Same value passed. Nothing was changed!
Status for Feedback with ID -> [9] was changed to {Unscheduled}.
Size for Story with ID -> [6] was changed to {Small}.
No <Story> with ID -> 8
Rating for Feedback with ID -> [8] was changed to {9}.
There is no value ('11', use numbers from 1 to 10) in Rating.
Cannot find user in team <Hackers> in order to assign task
Cannot find user in team <Hackers> in order to assign task
Cannot find user in team <Hackers> in order to assign task
Task with ID -> [4] was assigned to user <Alexa>.
Cannot find user in team <Hackers> in order to assign task
Task is already assigned to Alexa!
Task with ID -> [6] was assigned to user <Cortana>.
Task is already assigned to Cortana!
Task with ID -> [1] was assigned to user <Jimmy>.
Task with ID -> [2] was assigned to user <Jimmy>.
Task with ID -> [3] was assigned to user <Jimmy>.
No <Assignable task> with ID -> 9
<<< Alexa User's ACTIVITY HISTORY >>>
[30-December-2022 21:11:40] User was created.
[30-December-2022 21:11:40] User was added to team <Suicide Squad>
[30-December-2022 21:11:40] Task 'Another story' with ID -> [4] was assigned to <Alexa>
<<< <Hackers> board's ToDos ACTIVITY HISTORY >>>
[30-December-2022 21:11:40] Board was created.
[30-December-2022 21:11:40] Task 'Glitching interface' with ID -> [1] was added to board <ToDos>
[30-December-2022 21:11:40] Task 'Serious issue' with ID -> [2] was added to board <ToDos>
[30-December-2022 21:11:40] Task 'Fatal issue' with ID -> [3] was added to board <ToDos>
[30-December-2022 21:11:40] Task 'Funny story' with ID -> [5] was added to board <ToDos>
[30-December-2022 21:11:40] Task 'Great job here' with ID -> [7] was added to board <ToDos>
<<< <Suicide Squad> board's ToDos ACTIVITY HISTORY >>>
[30-December-2022 21:11:40] Board was created.
[30-December-2022 21:11:40] Task 'Third story' with ID -> [6] was added to board <ToDos>
<<< Bug ACTIVITY with ID -> [2] >>>
=== CHANGES HISTORY ===
[30-December-2022 21:11:40] Task with ID -> [2] was created.
[30-December-2022 21:11:40] Task is Unassigned
[30-December-2022 21:11:40] Steps to reproduce -> 1: Click register user button -> 2: Type your email -> 3: App crashes! -> 4: Fix it
[30-December-2022 21:11:40] The 'Status' of item with ID -> [2] switched from {Active} to {Fixed}
[30-December-2022 21:11:40] The 'Assignee' of item with ID -> [2] switched from {Unassigned} to {Jimmy}
=== NO COMMENTS ===
User <Marco Polo> added comment to task with ID -> [1]
User <Marco Polo> added comment to task with ID -> [1]
<<< Bug ACTIVITY with ID -> [1] >>>
=== CHANGES HISTORY ===
[30-December-2022 21:11:40] Task with ID -> [1] was created.
[30-December-2022 21:11:40] Task is Unassigned
[30-December-2022 21:11:40] Steps to reproduce -> 1: You have to open the interface -> 2: You will understand what I mean
[30-December-2022 21:11:40] The 'Assignee' of item with ID -> [1] switched from {Unassigned} to {Jimmy}
[30-December-2022 21:11:40] Comment added to task.
[30-December-2022 21:11:40] Comment added to task.
=== COMMENTS ===
"I like how you did this" - Marco Polo 
"Perfect job here" - Marco Polo 
Task with ID -> [1] was unassigned from user <Jimmy>.
Task with ID -> [5] is already unassigned.
No <Assignable task> with ID -> 7
=== ALL TEAMS ===
Team: Hackers
It has (1) users -> Jimmy
It contains (1) boards -> ToDos
===============
Team: Developers
It has (0) users
It contains (0) boards
===============
Team: Suicide Squad
It has (2) users -> Alexa, Cortana
It contains (2) boards -> ToDos, Goals
=== ALL PEOPLE ===
User: Alexa has (1) assigned tasks
Story: ID -> [4] 'Another story' | Status: Not Done | Priority: Medium | Size: Small | Assignee: Alexa
===============
User: Cortana has (1) assigned tasks
Story: ID -> [6] 'Third story' | Status: Not Done | Priority: Low | Size: Small | Assignee: Cortana
===============
User: Marco Polo has (0) assigned tasks
===============
User: Jimmy has (2) assigned tasks
Bug: ID -> [2] 'Serious issue' | Status: Fixed | Priority: Medium | Severity: Major | Assignee: Jimmy | Steps to reproduce: 
	1: Click register user button
	2: Type your email
	3: App crashes!
	4: Fix it
Bug: ID -> [3] 'Fatal issue' | Status: Active | Priority: High | Severity: Critical | Assignee: Jimmy | Steps to reproduce: 
	1: Open that
	2: Do this
<<< Bug ACTIVITY with ID -> [1] >>>
=== CHANGES HISTORY ===
[30-December-2022 21:11:40] Task with ID -> [1] was created.
[30-December-2022 21:11:40] Task is Unassigned
[30-December-2022 21:11:40] Steps to reproduce -> 1: You have to open the interface -> 2: You will understand what I mean
[30-December-2022 21:11:40] The 'Assignee' of item with ID -> [1] switched from {Unassigned} to {Jimmy}
[30-December-2022 21:11:40] Comment added to task.
[30-December-2022 21:11:40] Comment added to task.
[30-December-2022 21:11:40] The 'Assignee' of item with ID -> [1] switched from {Jimmy} to {Unassigned}
=== COMMENTS ===
"I like how you did this" - Marco Polo 
"Perfect job here" - Marco Polo 
<<< Hackers Team's ACTIVITY HISTORY >>>
[30-December-2022 21:11:40] Team was created.
[30-December-2022 21:11:40] Board <ToDos> was added to the team <Hackers>
[30-December-2022 21:11:40] User <Jimmy> was added to the team <Hackers>
<<< Jimmy User's ACTIVITY HISTORY >>>
[30-December-2022 21:11:40] User was created.
[30-December-2022 21:11:40] User was added to team <Hackers>
[30-December-2022 21:11:40] Task 'Glitching interface' with ID -> [1] was assigned to <Jimmy>
[30-December-2022 21:11:40] Task 'Serious issue' with ID -> [2] was assigned to <Jimmy>
[30-December-2022 21:11:40] Task 'Fatal issue' with ID -> [3] was assigned to <Jimmy>
[30-December-2022 21:11:40] Task 'Glitching interface' with ID -> [1] was unassigned from <Jimmy>
LIST TASKS WITH ASSIGNEE  
Bug: ID -> [2] 'Serious issue' | Status: Fixed | Priority: Medium | Severity: Major | Assignee: Jimmy | Steps to reproduce: 
	1: Click register user button
	2: Type your email
	3: App crashes!
	4: Fix it
===============
Bug: ID -> [3] 'Fatal issue' | Status: Active | Priority: High | Severity: Critical | Assignee: Jimmy | Steps to reproduce: 
	1: Open that
	2: Do this
===============
Story: ID -> [4] 'Another story' | Status: Not Done | Priority: Medium | Size: Small | Assignee: Alexa
===============
Story: ID -> [6] 'Third story' | Status: Not Done | Priority: Low | Size: Small | Assignee: Cortana
LIST TASKS WITH ASSIGNEE 
	-> filterByAssignee: "Jimmy" 
	-> filterByStatus: "Fixed" 
	-> sortByTitle  
Bug: ID -> [2] 'Serious issue' | Status: Fixed | Priority: Medium | Severity: Major | Assignee: Jimmy | Steps to reproduce: 
	1: Click register user button
	2: Type your email
	3: App crashes!
	4: Fix it
LIST ALL FEEDBACKS 
	-> sortByRating  
Feedback: ID -> [9] 'An Impressive job' | Status: Unscheduled | Rating: 10
===============
Feedback: ID -> [7] 'Great job here' | Status: New | Rating: 9
===============
Feedback: ID -> [8] 'Doing good' | Status: Unscheduled | Rating: 9
LIST ALL FEEDBACKS 
	-> filterByStatus: "New" 
	-> sortByRating  
Feedback: ID -> [7] 'Great job here' | Status: New | Rating: 9
LIST ALL BUGS 
	-> sortBySeverity  
Bug: ID -> [3] 'Fatal issue' | Status: Active | Priority: High | Severity: Critical | Assignee: Jimmy | Steps to reproduce: 
	1: Open that
	2: Do this
===============
Bug: ID -> [1] 'Glitching interface' | Status: Active | Priority: Low | Severity: Major | Assignee: Unassigned | Steps to reproduce: 
	1: You have to open the interface
	2: You will understand what I mean
===============
Bug: ID -> [2] 'Serious issue' | Status: Fixed | Priority: Medium | Severity: Major | Assignee: Jimmy | Steps to reproduce: 
	1: Click register user button
	2: Type your email
	3: App crashes!
	4: Fix it
LIST ALL BUGS 
	-> filterByStatus: "Active" 
	-> sortByTitle  
Bug: ID -> [3] 'Fatal issue' | Status: Active | Priority: High | Severity: Critical | Assignee: Jimmy | Steps to reproduce: 
	1: Open that
	2: Do this
===============
Bug: ID -> [1] 'Glitching interface' | Status: Active | Priority: Low | Severity: Major | Assignee: Unassigned | Steps to reproduce: 
	1: You have to open the interface
	2: You will understand what I mean
LIST ALL BUGS 
	-> filterByAssignee: "Alexa" 
	-> sortByPriority  
=== EMPTY LIST ===
LIST ALL STORIES 
	-> sortBySize  
Story: ID -> [5] 'Funny story' | Status: Not Done | Priority: High | Size: Medium | Assignee: Unassigned
===============
Story: ID -> [4] 'Another story' | Status: Not Done | Priority: Medium | Size: Small | Assignee: Alexa
===============
Story: ID -> [6] 'Third story' | Status: Not Done | Priority: Low | Size: Small | Assignee: Cortana
LIST ALL STORIES 
	-> filterByStatus: "InProgress" 
	-> sortByTitle  
=== EMPTY LIST ===
LIST ALL STORIES 
	-> filterByStatusAndAssignee: "InProgress" "Cortana" 
	-> sortByTitle  
=== EMPTY LIST ===
LIST ALL FEEDBACKS 
	-> filterByStatus: "Unscheduled" 
	-> sortByRating  
Feedback: ID -> [9] 'An Impressive job' | Status: Unscheduled | Rating: 10
===============
Feedback: ID -> [8] 'Doing good' | Status: Unscheduled | Rating: 9
LIST ALL TASKS  
Bug: ID -> [1] 'Glitching interface' | Status: Active | Priority: Low | Severity: Major | Assignee: Unassigned | Steps to reproduce: 
	1: You have to open the interface
	2: You will understand what I mean
===============
Bug: ID -> [2] 'Serious issue' | Status: Fixed | Priority: Medium | Severity: Major | Assignee: Jimmy | Steps to reproduce: 
	1: Click register user button
	2: Type your email
	3: App crashes!
	4: Fix it
===============
Bug: ID -> [3] 'Fatal issue' | Status: Active | Priority: High | Severity: Critical | Assignee: Jimmy | Steps to reproduce: 
	1: Open that
	2: Do this
===============
Story: ID -> [4] 'Another story' | Status: Not Done | Priority: Medium | Size: Small | Assignee: Alexa
===============
Story: ID -> [5] 'Funny story' | Status: Not Done | Priority: High | Size: Medium | Assignee: Unassigned
===============
Story: ID -> [6] 'Third story' | Status: Not Done | Priority: Low | Size: Small | Assignee: Cortana
===============
Feedback: ID -> [7] 'Great job here' | Status: New | Rating: 9
===============
Feedback: ID -> [8] 'Doing good' | Status: Unscheduled | Rating: 9
===============
Feedback: ID -> [9] 'An Impressive job' | Status: Unscheduled | Rating: 10
LIST ALL TASKS 
	-> filterByTitle: "Great job"  
Feedback: ID -> [7] 'Great job here' | Status: New | Rating: 9
LIST ALL TASKS 
	-> filterByTitle: "story" 
	-> sortByTitle  
Story: ID -> [4] 'Another story' | Status: Not Done | Priority: Medium | Size: Small | Assignee: Alexa
===============
Story: ID -> [5] 'Funny story' | Status: Not Done | Priority: High | Size: Medium | Assignee: Unassigned
===============
Story: ID -> [6] 'Third story' | Status: Not Done | Priority: Low | Size: Small | Assignee: Cortana
LIST ALL BUGS 
	-> filterByStatusAndAssignee: "Fixed" "Jimmy" 
	-> sortByPriority  
Bug: ID -> [2] 'Serious issue' | Status: Fixed | Priority: Medium | Severity: Major | Assignee: Jimmy | Steps to reproduce: 
	1: Click register user button
	2: Type your email
	3: App crashes!
	4: Fix it

```
### Use Cases Sample Input
#### №1 Input
```
UseCase№1
CreateTeam Seniors
CreateBoardInTeam Issues Seniors
CreateTaskInBoard bug Issues Seniors {{The program freezes when the LogIn is clicked}} {{This needs to be fixed quickly!}} high critical {{Open the application}} {{Click "Log In"}} {{The application freezes!}}
CreatePerson Tomas
AddPersonToTeam Tomas Seniors
AssignTask 1 Tomas
ShowTaskActivity 1

```
#### №1 Output
```
Team with a name <Seniors> was created.
Board <Issues> has been created in team <Seniors>!
Bug <The program freezes when the LogIn is clicked> with ID -> [1] has been created in board <Issues>!
User with a name <Tomas> was created.
Person <Tomas> has been added to the team <Seniors>!
Task with ID -> [1] was assigned to user <Tomas>.
<<< Bug ACTIVITY with ID -> [1] >>>
=== CHANGES HISTORY ===
[30-December-2022 21:16:28] Task with ID -> [1] was created.
[30-December-2022 21:16:28] Task is Unassigned
[30-December-2022 21:16:28] Steps to reproduce -> 1: Open the application -> 2: Click "Log In" -> 3: The application freezes!
[30-December-2022 21:16:28] The 'Assignee' of item with ID -> [1] switched from {Unassigned} to {Tomas}
=== NO COMMENTS ===
```

#### №2 Input
```
CreateTeam Juniors
CreateBoardInTeam Issues Juniors
CreateTaskInBoard bug Issues Juniors {{The program freezes when the LogIn is clicked}} {{This needs to be fixed quickly!}} high critical {{Open the application}} {{Click "Log In"}}
CreateTaskInBoard bug Issues Juniors {{Serious issue}} {{App does not work}} medium major {{Click register user button}} {{Type your email}} {{App crashes!}} {{Fix it}}
CreateTaskInBoard bug Issues Juniors {{Fatal issue}} {{Server is down}} high critical {{Open that }} {{Do this}}
CreatePerson Clare
AddPersonToTeam Clare Juniors
ListAllBugs sortBySeverity
AssignTask 1 Clare
AssignTask 3 Clare

```
#### №2 Output
```
Team with a name <Juniors> was created.
Board <Issues> has been created in team <Juniors>!
Bug <The program freezes when the LogIn is clicked> with ID -> [1] has been created in board <Issues>!
Bug <Serious issue> with ID -> [2] has been created in board <Issues>!
Bug <Fatal issue> with ID -> [3] has been created in board <Issues>!
User with a name <Clare> was created.
Person <Clare> has been added to the team <Juniors>!
LIST ALL BUGS 
	-> sortBySeverity  
Bug: ID -> [1] 'The program freezes when the LogIn is clicked' | Status: Active | Priority: High | Severity: Critical | Assignee: Unassigned | Steps to reproduce: 
	1: Open the application
	2: Click "Log In"
===============
Bug: ID -> [3] 'Fatal issue' | Status: Active | Priority: High | Severity: Critical | Assignee: Unassigned | Steps to reproduce: 
	1: Open that
	2: Do this
===============
Bug: ID -> [2] 'Serious issue' | Status: Active | Priority: Medium | Severity: Major | Assignee: Unassigned | Steps to reproduce: 
	1: Click register user button
	2: Type your email
	3: App crashes!
	4: Fix it
Task with ID -> [1] was assigned to user <Clare>.
Task with ID -> [3] was assigned to user <Clare>.
```
#### №3 Input
```
CreateTeam Juniors
CreateBoardInTeam Issues Juniors
CreateTaskInBoard bug Issues Juniors {{The program freezes when the LogIn is clicked}} {{This needs to be fixed quickly!}} high critical {{Open the application}} {{Click "Log In"}}
CreatePerson Clare
AddPersonToTeam Clare Juniors
AssignTask 1 Clare
AddComment 1 {{This one took me a while, but it is fixed now!}} Clare
ChangeBug 1 status fixed
ShowTaskActivity 1

```
#### №3 Output
```
Team with a name <Juniors> was created.
Board <Issues> has been created in team <Juniors>!
Bug <The program freezes when the LogIn is clicked> with ID -> [1] has been created in board <Issues>!
User with a name <Clare> was created.
Person <Clare> has been added to the team <Juniors>!
Task with ID -> [1] was assigned to user <Clare>.
User <Clare> added comment to task with ID -> [1]
Status for Bug with ID -> [1] was changed to {Fixed}.
<<< Bug ACTIVITY with ID -> [1] >>>
=== CHANGES HISTORY ===
[30-December-2022 21:19:35] Task with ID -> [1] was created.
[30-December-2022 21:19:35] Task is Unassigned
[30-December-2022 21:19:35] Steps to reproduce -> 1: Open the application -> 2: Click "Log In"
[30-December-2022 21:19:35] The 'Assignee' of item with ID -> [1] switched from {Unassigned} to {Clare}
[30-December-2022 21:19:35] Comment added to task.
[30-December-2022 21:19:35] The 'Status' of item with ID -> [1] switched from {Active} to {Fixed}
=== COMMENTS ===
"This one took me a while, but it is fixed now!" - Clare 
```
