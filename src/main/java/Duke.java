import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        final String LOGO = " ____        _\n"
                + "|  _ \\ _   _| | _____\n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        final String SINGLE_LINE =
                "____________________________________________________________";

        //Greet Component
        System.out.println(SINGLE_LINE);
        System.out.println(LOGO);
        System.out.println("Hello! I'm Duke!");
        System.out.println("What can I do for you?");
        System.out.println(SINGLE_LINE);

        //Interaction Component
        Scanner scanner = new Scanner(System.in);
        String userInput;
        boolean stillInteracting = true;
        ArrayList<Task> tasks = new ArrayList<>();

        while (stillInteracting) {
            userInput = scanner.nextLine();

            //Identifying the Command Component
            String[] inputParts = userInput.trim().split("\\s+", 2);

            //Check and process the 'done' command
            int taskToFinish = 0;
            String taskName = "";
            String dueDate = "";
            switch (inputParts[0]) {
            case "done":
                if (inputParts.length > 1) {
                    //Change from 0-based to 1-base indexing by deducting 1
                    taskToFinish = Integer.parseInt(inputParts[1]) - 1;
                    userInput = "done";
                } else {
                    System.out.println("Please choose a task to complete!");
                    userInput = "list";
                }
                break;
            case "todo":
                if (inputParts.length > 1) {
                    taskName = inputParts[1];
                    userInput = "todo";
                } else {
                    userInput = "invalid";
                }
                break;
            case "deadline":
                if (inputParts.length > 1) {
                    String[] deadlineParts = inputParts[1].trim().split("/by", 2);
                    //Input validation check
                    if (deadlineParts.length > 1) {
                        userInput = "deadline";
                        taskName = deadlineParts[0];
                        dueDate = deadlineParts[1];
                    } else {
                        userInput = "invalid";
                    }
                } else {
                    userInput = "invalid";
                }
                break;
            case "event":
                if (inputParts.length > 1) {
                    String[] eventParts = inputParts[1].trim().split("/at", 2);
                    //Input validation check
                    if (eventParts.length > 1) {
                        userInput = "event";
                        taskName = eventParts[0];
                        dueDate = eventParts[1];
                    } else {
                        userInput = "invalid";
                    }
                } else {
                    userInput = "invalid";
                }
                break;
            }

            //Carrying out the Command Component
            switch (userInput) {
            case "bye":
                stillInteracting = false;
                break;
            case "list":
                if (tasks.size() == 0) {
                    System.out.println("Fortunately, you have no tasks due");
                } else {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ": " + tasks.get(i));
                    }
                }
                System.out.println(SINGLE_LINE);
                break;
            case "todo":
                Todo newTodo = new Todo(taskName);
                tasks.add(newTodo);
                System.out.println(SINGLE_LINE);
                System.out.println("+ " + newTodo);
                System.out.println("You now have " + Task.getTotalTasks() + " remaining task");
                System.out.println(SINGLE_LINE);
                break;
            case "deadline":
                Deadline newDeadLine = new Deadline(taskName, dueDate);
                tasks.add(newDeadLine);
                System.out.println(SINGLE_LINE);
                System.out.println("+ " + newDeadLine);
                System.out.println("You now have " + Task.getTotalTasks() + " remaining task");
                System.out.println(SINGLE_LINE);
                break;
            case "event":
                Event newEvent = new Event(taskName, dueDate);
                tasks.add(newEvent);
                System.out.println(SINGLE_LINE);
                System.out.println("+ " + newEvent);
                System.out.println("You now have " + Task.getTotalTasks() + " remaining task");
                System.out.println(SINGLE_LINE);
                break;
            case "done":
                if (taskToFinish < 0 || taskToFinish >= tasks.size()) {
                    System.out.println("That Task does not exist!");
                } else if (Task.getTotalTasks() == 0) {
                    System.out.println("No remaining tasks");
                } else if (tasks.get(taskToFinish).isCompleted()) {
                    System.out.println("That Task has already been completed, but let's shoot it again");
                } else {
                    //Set the task to be completed and check remaining tasks.
                    tasks.get(taskToFinish).setCompleted(true);
                    System.out.println("Task: '" + tasks.get(taskToFinish).getTaskName().trim() +
                            "' marked as completed, well done!");
                    if(Task.getTotalTasks() == 0) {
                        System.out.println("All Task Completed!");
                    } else {
                        System.out.println("You have " + Task.getTotalTasks() + " remaining tasks");
                        System.out.println("We'll get them next time!");
                    }
                }
                System.out.println(SINGLE_LINE);
                break;
            default:
                System.out.println("Invalid option, Try again!" + System.lineSeparator());

                break;
            }
        }

        //Exit Component
        System.out.println(SINGLE_LINE);
        System.out.println("Bye! Hope to see you again soon!");
        System.out.println(SINGLE_LINE);
    }
}
