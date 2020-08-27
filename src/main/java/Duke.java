import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        final String LOGO = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
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
        String userInput = "";
        boolean stillInteracting = true;
        ArrayList<Task> tasks = new ArrayList<Task>();

        while (stillInteracting) {
            userInput = scanner.nextLine();
            String[] inputParts = userInput.split(" ");

            //Check and process the 'done' command
            int taskToFinish = 0;
            if (inputParts[0].equals("done")) {
                if (inputParts.length > 1) {
                    //Change from 0-based to 1-base indexing by deducting 1
                    taskToFinish = Integer.parseInt(inputParts[1]) - 1;
                    userInput = "done";
                } else {
                    System.out.println("Please choose a task to complete!");
                    userInput = "list";
                }
            }

            switch (userInput) {
            case "bye":
                stillInteracting = false;
                break;
            case "list":
                if (tasks.size() == 0) {
                    System.out.println("Fortunately, you have no tasks due");
                } else {
                    for (int i = 0; i < tasks.size(); i++) {
                        Task currTask = tasks.get(i);
                        System.out.printf("%d: %s %s%n", i + 1, currTask.getTaskName(),
                                currTask.getStatusIndicator());
                    }
                }
                System.out.println(SINGLE_LINE);
                break;
            case "done":
                if (taskToFinish < 0 || taskToFinish > tasks.size() || Task.getTotalTasks() == 0) {
                    System.out.println("That Task does not exist!");
                } else if (tasks.get(taskToFinish).isCompleted()) {
                    System.out.println("That Task has already been completed, but let's shoot it again");
                } else {
                    //Set the task to be completed and check remaining tasks.
                    tasks.get(taskToFinish).setCompleted(true);
                    System.out.println("Task: '" + tasks.get(taskToFinish).getTaskName() +
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
                Task newTask = new Task(userInput);
                tasks.add(newTask);
                System.out.println(SINGLE_LINE);
                System.out.println("+ " + userInput);
                System.out.println(SINGLE_LINE);
                break;
            }
        }

        //Exit Component
        System.out.println(SINGLE_LINE);
        System.out.println("Bye! Hope to see you again soon!");
        System.out.println(SINGLE_LINE);
    }
}
