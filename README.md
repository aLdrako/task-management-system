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
- ShowBoardActivity `{String boardName} {String teamName}`
- CreateTaskInBoard `[bug/story/feedback](enums) {String boardName} {String teamName} {(coresponding task parameters)}`
- AddStepsToBug `{ind id} {String steps} {String steps} ...`
- AssignTask `{ind id} {String name}`
- UnassignTask `{ind id} {String name}`
- AddComment `{ind id} {String comment} {String name}`
- ChangeTask `{ind id} [status/priority/severity/size/rating](enums)`
- ShowTaskActivity `{ind id}`
- ListAllTasks `[sortByTitle] || [filterByTitle {String title}] || [filterByTitle {String title} sortByTitle]` 
- ListAllBugs `[sortByTitle/sortByPriority/sortBySeverity] || [filterByStatus/filterByAssignee/filterByStatusAndAssignee {String status/assignee/status assignee}] || [filterByStatus/filterByAssignee/filterByStatusAndAssignee {String status/assignee/status assignee} sortByTitle/sortByPriority/sortBySeverity]` 
- ListAllStories `[sortByTitle/sortByPriority/sortBySize] || [filterByStatus/filterByAssignee/filterByStatusAndAssignee {String status/assignee/status assignee}] || [filterByStatus/filterByAssignee/filterByStatusAndAssignee {String status/assignee/status assignee} sortByTitle/sortByPriority/sortBySize]`
- ListAllFeedbacks `[sortByTitle/sortByRating] || [filterByStatus {String status}] || [filterByStatus {String status} sortByTitle/sortByPriority]`
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
CreateBoardInTeam ToDos Hackers
CreateBoardInTeam Goals {{Suicide Squad}}
CreateTaskInBoard bug ToDos Hackers {{Glitching interface}} {{There is a broken interface}} low major
CreateTaskInBoard bug ToDos Hackers {{Fatal issue}} {{Server is down}} high critical
CreateTaskInBoard bug ToDos Hackers {{Serious issue}} {{App does not work}} medium major
CreateTaskInBoard bug ToDos Hackers {{Serious issue}} {{App does not work}} medium large
CreateTaskInBoard bug Ultimate Hackers {{Serious issue}} {{App does not work}} medium large
CreateTaskInBoard story Goals {{Suicide Squad}} {{Another story}} {{Once upon a time there was a cat....}} medium small
CreateTaskInBoard story ToDos Hackers {{Funny story}} {{Once upon a time there was a mouse....}} high medium
CreateTaskInBoard story ToDos Hackers {{Third story}} {{Once upon a time there was a dog....}} low large
CreateTaskInBoard feedback ToDos Hackers {{Great job here}} {{This developer is doing great}} 9
CreateTaskInBoard feedback Goals {{Suicide Squad}} {{Doing good}} {{There is still room for improvement}} 7
CreateTaskInBoard feedback Goals {{Suicide Squad}} {{An Impressive job}} {{Thats what I call excellent work}} 10
CreateTaskInBoard feedback Goals {{Suicide Squad}} {{An Impressive job}} {{Thats what I call excellent work}} 11
AddStepsToBug 1 {{First open the app}} {{Try to access the database}}
AddStepsToBug 4 {{First open the app}} {{Try to access the database}}
AddStepsToBug 2 {{Start the system}} {{Open the application}} {{Perform data conversion}} {{Save progress}}
AddPersonToTeam Alexa {{Suicide Squad}}
AddPersonToTeam Cortana {{Suicide Squad}}
AssignTask 1 Alexa
AssignTask 5 Alexa
AssignTask 2 Alexa
AssignTask 6 Alexa
AssignTask 3 Cortana
UnassignTask 1 Ivan
UnassignTask 12 Cortana
UnassignTask 6 Alexa
AssignTask 6 Cortana
AddComment 3 {{Thank you so much for the amazing feedback}} Cortana
AddComment 3 {{It was an easy work}} Alexa
ChangeTask 2 status Fixed
ChangeTask 1 priority High
ChangeTask 1 rating High
ChangeTask 3 severity Minor
ChangeTask 3 severity Minor
ChangeTask 6 status InProgress
ChangeTask 7 status Done
ChangeTask 7 rating 8
ShowTaskActivity 3
ShowTaskActivity 7
ShowAllTeams
ShowAllPeople
ShowTeamActivity Hackers
ShowTeamActivity {{Suicide Squad}}
ShowBoardActivity ToDos Hackers
ShowPersonActivity Cortana
ShowAllTeamMembers {{Suicide Squad}}
ShowAllTeamBoards Hackers
ListAllFeedbacks sortByRating
ListAllFeedbacks filterByStatus New sortByRating
ListAllBugs sortBySeverity
ListAllBugs filterByStatus Active sortByTitle
ListAllBugs filterByAssignee Alexa sortByPriority
ListAllStories sortBySize
ListAllStories filterByStatus InProgress sortByTitle
ListAllStories filterByStatusAndAssignee InProgress Cortana sortByTitle
ListAllTasks sortByTitle
ListAllTasks filterByTitle {{Great job}}
ListAllTasks filterByTitle story sortByTitle

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
Board ToDos has been created in team Hackers!
Board Goals has been created in team Suicide Squad!
Task 'Glitching interface' with ID [1] has been created in board 'ToDos'!
Task 'Fatal issue' with ID [2] has been created in board 'ToDos'!
Task 'Serious issue' with ID [3] has been created in board 'ToDos'!
There is no value (large) in SeverityType.
There is no Board with name 'Ultimate' in Team 'Hackers'!
Task 'Another story' with ID [4] has been created in board 'Goals'!
Task 'Funny story' with ID [5] has been created in board 'ToDos'!
Task 'Third story' with ID [6] has been created in board 'ToDos'!
Task 'Great job here' with ID [7] has been created in board 'ToDos'!
Task 'Doing good' with ID [8] has been created in board 'Goals'!
Task 'An Impressive job' with ID [9] has been created in board 'Goals'!
There is no value ('11', use numbers from 1 to 10) in Rating.
Steps to reproduce added to Bug with ID 1
Provided task with ID 4 does not belong to Bug category!
Steps to reproduce added to Bug with ID 2
Person Alexa has been added to the team Suicide Squad!
Person Cortana has been added to the team Suicide Squad!
Task with ID 1 was assigned to user Alexa.
Task with ID 5 was assigned to user Alexa.
Task with ID 2 was assigned to user Alexa.
Task with ID 6 was assigned to user Alexa.
Task with ID 3 was assigned to user Cortana.
There is no User or Team with name Ivan!
No task with ID 12
Task with ID 6 was unassigned from user Alexa.
Task with ID 6 was assigned to user Cortana.
User Cortana added comment to task with ID 3
User Alexa added comment to task with ID 3
Status for Bug with ID 2 was changed to Fixed.
Priority for Bug with ID 1 was changed to High.
Bug does not have 'Rating' setting!
Severity for Bug with ID 3 was changed to Minor.
Same value passed. Nothing was changed!
Status for Story with ID 6 was changed to InProgress.
Status for Feedback with ID 7 was changed to Done.
Rating for Feedback with ID 7 was changed to 8.
<<< Bug Activity with ID -> [3] >>>
=== CHANGES HISTORY ===
[26-December-2022 19:45:45] Task with ID 3 was created.
[26-December-2022 19:45:45] Bug is unassigned
[26-December-2022 19:45:45] Bug was assigned to: Cortana
[26-December-2022 19:45:45] Comment added to task.
[26-December-2022 19:45:45] Comment added to task.
[26-December-2022 19:45:45] The severity of item with ID 3 switched from 'Major' to 'Minor'
=== COMMENTS ===
"Thank you so much for the amazing feedback" - Cortana 
"It was an easy work" - Alexa 
<<< Feedback Activity with ID -> [7] >>>
=== CHANGES HISTORY ===
[26-December-2022 19:45:45] Task with ID 7 was created.
[26-December-2022 19:45:45] The status of item with ID 7 switched from 'New' to 'Done'
[26-December-2022 19:45:45] The rating of item with ID 7 switched from '9' to '8'
=== NO COMMENTS ===
=== ALL TEAMS ===
Team: Hackers
It has (0) users -> 
It contains (1) boards -> ToDos
===============
Team: Developers
It has (0) users -> 
It contains (0) boards -> 
===============
Team: Suicide Squad
It has (2) users -> Alexa, Cortana
It contains (1) boards -> Goals

=== ALL PEOPLE ===
User: Alexa has (3) assigned tasks
Bug: ID -> [1] 'Glitching interface' | Status: Active | Priority: High | Severity: Major | Assignee: Alexa | Steps to reproduce: 
	-> First open the app
	-> Try to access the database
Story: ID -> [5] 'Funny story' | Status: Not Done | Priority: High | Size: Medium | Assignee: Alexa
Bug: ID -> [2] 'Fatal issue' | Status: Fixed | Priority: High | Severity: Critical | Assignee: Alexa | Steps to reproduce: 
	-> Start the system
	-> Open the application
	-> Perform data conversion
	-> Save progress
<<< Alexa's Activity History >>>
[26-December-2022 19:45:45] User was created.
[26-December-2022 19:45:45] Task 'Glitching interface' assigned to Alexa
[26-December-2022 19:45:45] Task 'Funny story' assigned to Alexa
[26-December-2022 19:45:45] Task 'Fatal issue' assigned to Alexa
[26-December-2022 19:45:45] Task 'Third story' assigned to Alexa
[26-December-2022 19:45:45] Task 'Third story' unassigned from Alexa
[26-December-2022 19:45:45] Added comment to task with ID 3
===============
User: Cortana has (2) assigned tasks
Bug: ID -> [3] 'Serious issue' | Status: Active | Priority: Medium | Severity: Minor | Assignee: Cortana | Steps to reproduce: Not specified
Story: ID -> [6] 'Third story' | Status: InProgress | Priority: Low | Size: Large | Assignee: Cortana
<<< Cortana's Activity History >>>
[26-December-2022 19:45:45] User was created.
[26-December-2022 19:45:45] Task 'Serious issue' assigned to Cortana
[26-December-2022 19:45:45] Task 'Third story' assigned to Cortana
[26-December-2022 19:45:45] Added comment to task with ID 3

<<< Hackers's Activity History >>>
[26-December-2022 19:45:45] Team was created.
===============
[26-December-2022 19:45:45] Board ToDos added to the team Hackers
<<< Suicide Squad's Activity History >>>
[26-December-2022 19:45:45] Team was created.
===============
[26-December-2022 19:45:45] Board Goals added to the team Suicide Squad
===============
[26-December-2022 19:45:45] User Alexa added to the team Suicide Squad
===============
[26-December-2022 19:45:45] User Cortana added to the team Suicide Squad
<<< ToDos's Activity History >>>
[26-December-2022 19:45:45] Board was created.
===============
[26-December-2022 19:45:45] Task Glitching interface added to board ToDos
===============
[26-December-2022 19:45:45] Task Fatal issue added to board ToDos
===============
[26-December-2022 19:45:45] Task Serious issue added to board ToDos
===============
[26-December-2022 19:45:45] Task Funny story added to board ToDos
===============
[26-December-2022 19:45:45] Task Third story added to board ToDos
===============
[26-December-2022 19:45:45] Task Great job here added to board ToDos
Cortana's user activity:
[26-December-2022 19:45:45] User was created.
[26-December-2022 19:45:45] Task 'Serious issue' assigned to Cortana
[26-December-2022 19:45:45] Task 'Third story' assigned to Cortana
[26-December-2022 19:45:45] Added comment to task with ID 3

Suicide Squad' team members: Alexa, Cortana 
Hackers' team boards: ToDos 
<<< LIST ALL FEEDBACKS>>>
Feedback: ID -> [8] 'Doing good' | Status: New | Rating: 7
===============
Feedback: ID -> [7] 'Great job here' | Status: Done | Rating: 8
===============
Feedback: ID -> [9] 'An Impressive job' | Status: New | Rating: 10
<<< LIST ALL FEEDBACKS>>>
Feedback: ID -> [8] 'Doing good' | Status: New | Rating: 7
===============
Feedback: ID -> [9] 'An Impressive job' | Status: New | Rating: 10
<<< LIST ALL BUGS>>>
Bug: ID -> [2] 'Fatal issue' | Status: Fixed | Priority: High | Severity: Critical | Assignee: Alexa | Steps to reproduce: 
	-> Start the system
	-> Open the application
	-> Perform data conversion
	-> Save progress
===============
Bug: ID -> [1] 'Glitching interface' | Status: Active | Priority: High | Severity: Major | Assignee: Alexa | Steps to reproduce: 
	-> First open the app
	-> Try to access the database
===============
Bug: ID -> [3] 'Serious issue' | Status: Active | Priority: Medium | Severity: Minor | Assignee: Cortana | Steps to reproduce: Not specified
<<< LIST ALL BUGS>>>
Bug: ID -> [1] 'Glitching interface' | Status: Active | Priority: High | Severity: Major | Assignee: Alexa | Steps to reproduce: 
	-> First open the app
	-> Try to access the database
===============
Bug: ID -> [3] 'Serious issue' | Status: Active | Priority: Medium | Severity: Minor | Assignee: Cortana | Steps to reproduce: Not specified
<<< LIST ALL BUGS>>>
Bug: ID -> [1] 'Glitching interface' | Status: Active | Priority: High | Severity: Major | Assignee: Alexa | Steps to reproduce: 
	-> First open the app
	-> Try to access the database
===============
Bug: ID -> [2] 'Fatal issue' | Status: Fixed | Priority: High | Severity: Critical | Assignee: Alexa | Steps to reproduce: 
	-> Start the system
	-> Open the application
	-> Perform data conversion
	-> Save progress
<<< LIST ALL STORIES>>>
Story: ID -> [6] 'Third story' | Status: InProgress | Priority: Low | Size: Large | Assignee: Cortana
===============
Story: ID -> [5] 'Funny story' | Status: Not Done | Priority: High | Size: Medium | Assignee: Alexa
===============
Story: ID -> [4] 'Another story' | Status: Not Done | Priority: Medium | Size: Small | Assignee: Unassigned
<<< LIST ALL STORIES>>>
Story: ID -> [6] 'Third story' | Status: InProgress | Priority: Low | Size: Large | Assignee: Cortana
<<< LIST ALL STORIES>>>
Story: ID -> [6] 'Third story' | Status: InProgress | Priority: Low | Size: Large | Assignee: Cortana
<<< LIST ALL TASKS>>>
Feedback: ID -> [9] 'An Impressive job' | Status: New | Rating: 10
===============
Story: ID -> [4] 'Another story' | Status: Not Done | Priority: Medium | Size: Small | Assignee: Unassigned
===============
Feedback: ID -> [8] 'Doing good' | Status: New | Rating: 7
===============
Bug: ID -> [2] 'Fatal issue' | Status: Fixed | Priority: High | Severity: Critical | Assignee: Alexa | Steps to reproduce: 
	-> Start the system
	-> Open the application
	-> Perform data conversion
	-> Save progress
===============
Story: ID -> [5] 'Funny story' | Status: Not Done | Priority: High | Size: Medium | Assignee: Alexa
===============
Bug: ID -> [1] 'Glitching interface' | Status: Active | Priority: High | Severity: Major | Assignee: Alexa | Steps to reproduce: 
	-> First open the app
	-> Try to access the database
===============
Feedback: ID -> [7] 'Great job here' | Status: Done | Rating: 8
===============
Bug: ID -> [3] 'Serious issue' | Status: Active | Priority: Medium | Severity: Minor | Assignee: Cortana | Steps to reproduce: Not specified
===============
Story: ID -> [6] 'Third story' | Status: InProgress | Priority: Low | Size: Large | Assignee: Cortana
<<< LIST ALL TASKS>>>
Feedback: ID -> [7] 'Great job here' | Status: Done | Rating: 8
<<< LIST ALL TASKS>>>
Story: ID -> [4] 'Another story' | Status: Not Done | Priority: Medium | Size: Small | Assignee: Unassigned
===============
Story: ID -> [5] 'Funny story' | Status: Not Done | Priority: High | Size: Medium | Assignee: Alexa
===============
Story: ID -> [6] 'Third story' | Status: InProgress | Priority: Low | Size: Large | Assignee: Cortana

```