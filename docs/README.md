# User Guide
Duke is a flexible command line application that is useful for managing tasks.

## Quick Start
1. Ensure you have Java 11 installed on your computer.
2. Download the latest `ip.jar`.
3. Move the jar file to the folder that will be used as the working directory for this application.
4. Run the following command to execute the program.
5. Here are some sample commands to get you started.
6. Refer to Usage or Command Summary section for more details.

## Features 
### Task Manager
Manages different types of tasks, allows creation, completion and deletion of tasks.

### Search tools
Includes various features that will aid in finding tasks by specific names or due dates.

### Persistent Task list
All tasks and changes will be saved to a local file. Future use of Duke will load from this save file.

## Usage
### Listing all Tasks `list`
This will list all task that the user has added.

Format: `list`

Expected Outcome:
```
1: [D][✗] return book (by:June 6th)
2: [E][✗] project meeting (at:Aug 6th 2-4pm)
3: [T][✓] join sports club
```

### Adding a Todo task `todo`
This will add a Todo task to the task list.

Format: `todo task_name`

Sample Input: `todo new task`

The expected outcome:
```
+ [T][✗] new task
You a total of tasks remaining: 25
```
### Adding a Deadline task `deadline`
This will add a deadline task to the task list.

Format: 
1. `deadline task_name /by due_by`
2. `deadline task_name /by YYYY-MM-DD HHmm`

Notes: 
- Format '2' allows Duke to find this deadline with `due` command.
- Format '1' allows user to store any string, but will be non-searchable by 'due'.

Sample Input: 
1. `deadline CS2105 Assignment 1/by Wednesday`
2. `deadline Finish this userguide/by2020-9-20 1450`

The expected outcome:
```
+ [D][✗] CS2105 Assignment 1 (by: Wednesday!)
You a total of tasks remaining: 26
+ [D][✗] Finish this userguide (by:20/Sep/2020 02:50 PM)
You a total of tasks remaining: 27
```

### Adding an Event task `event`
This will add an event task to the task list.

Format: 
1. `event task_name /at due_date`
2. `event task_name /at YYYY-MM-DD HHmm`

Notes: 
- Format 2 allows Duke to find this deadline with `due` command.
- Format '1' allows user to store any string, but will be non-searchable by 'due'.

Sample Input: 
1. `event Meet my friends!(jk I have no friends)/at Never apparently :|`
2. `event CS2113T tP meeting/at2020-09-25 2000`

The expected outcome:
```
+ [E][✗] Meet my friends!(jk I have no friends) (at: Never apparently :|)
You a total of tasks remaining: 28
+ [E][✗] CS2113T tP meeting (at:25/Sep/2020 08:00 PM)
You a total of tasks remaining: 29
```
### Completing a Task `done`
This will mark a task as completed.

Format: `done task_id`

Notes:
- task_id refers to the index number displayed when the command 'list' is used.
- task_id field must be a positive integer.

Sample Input: `done 32`

The expected outcome:
```
'Meet my friends!(jk I have no friends)'
marked as completed, well done!
You a total of tasks remaining: 28
```
### Deleting a Task `delete`
This will remove a task from the task list.

Format: `delete task_id`

Notes:
- task_id refers to the index number displayed when the command 'list' is used.
- task_id field must be a positive integer.

Sample Input: `delete 32`

The expected outcome:
```
Noted! I have removed the task:
[E][✓] Meet my friends!(jk I have no friends) (at: Never apparently :()
```

### Find by Keyword `find`
Searches and prints all tasks that has the search string in the task name.

Format: `find search_term`

Sample Input: `find meeting`

The expected outcome:
```
[E][✗] project meeting (at:Aug 6th 2-4pm)
[E][✗] project meeting 2 (at:Mon 2-4pm)
[E][✗] CS2113T tP meeting (at:25/Sep/2020 08:00 PM)
```
### Find by Due Date `due`
Searches and prints all tasks that has the specified due date.

Format: `due search_date`

Notes:
- The 'search_date' must be in the following format 'YYYY-MM-DD'.

Sample Input: `due 2020-9-25`

The expected outcome:
```
[E][✗] CS2113T tP meeting (at:25/Sep/2020 08:00 PM)
[D][✗] Enjoy mandatory break (by:25/Sep/2020 10:00 PM)
```

### Exiting the program `bye`
Terminates the program and exits gracefully.

Format: `bye`

Sample Input: `bye`

The expected outcome:
```
Bye! Hope to see you again soon!
```

## FAQ
Q: Why do I keep getting "Error: Please provide more information"

A: This error message indicates that you did not provide enough information for the command to work on.
You can refer to the sample input for more information.

Q: Where does Duke store my save file?

A: It will create a data/duke.txt in the working directory that Duke was called from.


## Command Summary

|Feature|Command|
|---    |---|
| List all Tasks                | `list` |
| Add a Todo task               | `todo task_name` |
| Add a Deadline task           | `deadline task_name /by due_by` <br> `deadline task_name /by YYYY-MM-DD` |
| Add an Event task             | `event task_name /at due_date` <br> `event task_name /at YYYY-MM-DD`    |
| Mark a task as complete       | `done task_id` |
| Delete a task from the list   | `delete task_id` |
| Find a specific task          | `find search_term` |
| Find task on a specific date  | `due search_date`  |
| Exit                          | `bye` | 