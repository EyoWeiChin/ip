package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static final String LOGO = " ____        _" + System.lineSeparator()
            + "|  _ \\ _   _| | _____" + System.lineSeparator()
            + "| | | | | | | |/ / _ \\" + System.lineSeparator()
            + "| |_| | |_| |   <  __/" + System.lineSeparator()
            + "|____/ \\__,_|_|\\_\\___|" + System.lineSeparator();

    private static final String SINGLE_LINE =
            "____________________________________________________________";

    //Constant variables for Command logic
    private static final String RAW_COMMAND_DELIMIT = " ";
    private static final int SPLIT_INPUT_LIMIT = 2;

    private static final String COMMAND_TODO = "todo";
    private static final String DELIMIT_TODO = "";

    private static final String COMMAND_DONE = "done";
    private static final String DELIMIT_DONE = "";

    private static final String COMMAND_DEADLINE = "deadline";
    private static final String DELIMIT_DEADLINE = "/by";

    private static final String COMMAND_EVENT = "event";
    private static final String DELIMIT_EVENT = "/at";

    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_BYE = "bye";

    private static final String INVALID_OPTION = "invalid";
    private static final String INIT_STRING = "";

    /**
     * ArrayList of all Tasks
     */
    private static final ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Declare scanner to read from I/O
     */
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Declare Messages to be used by Duke here.
     */
    private static final String MESSAGE_NO_SUCH_TASK = "That Task does not exist!";
    private static final String MESSAGE_NO_REMAINING_TASKS = "No remaining tasks";
    private static final String MESSAGE_TASK_ALREADY_COMPLETED = "That Task has already " +
            "been completed, but let's shoot it again";
    private static final String MESSAGE_TASK_COMPLETED = "marked as completed, well done!";
    private static final String MESSAGE_ALL_TASK_COMPLETED = "All Task Completed!";
    private static final String MESSAGE_TASKS_LEFT = "You a total of tasks remaining: ";
    private static final String MESSAGE_NO_TASKS_DUE = "Fortunately, you have no tasks due";
    private static final String MESSAGE_GOODBYE = "Bye! Hope to see you again soon!";
    private static final String MESSAGE_GREETING = "Hello! I'm Duke!" + System.lineSeparator()
            + "What can I do for you?";
    private static final String MESSAGE_INVALID_OPTION = "Invalid option, Try again!"
            + System.lineSeparator();
    private static final String ERROR_MESSAGE_NO_INFO = "Error: Please provide more information!";

    /**
     * Main entry point of the application
     * Greets the User and begins User interaction
     */
    public static void main(String[] args) {
        printWelcomeScreen();
        loadTaskList();
        //Interaction Component
        boolean stillInteracting = true;
        while (stillInteracting) {
            String[] processedInputs = processInput(getUserInput());
            stillInteracting = executeCommand(processedInputs);
        }
    }

    /**
     * Returns the raw user input into processed parts for execution based on found keywords
     *
     * @param userInput raw input from user
     * @return processedInputs String array with the input in parts as follows
     * processedInputs[0] the processed Command e.g COMMAND_DONE, COMMAND_TODO, ...
     * processedInputs[1] taskName e.g 'borrow book' or 1 (taskID for done commands)
     * processedInputs[2] = taskParameter, e.g 'Sunday', 'Mon 2pm-4pm'
     */
    private static String[] processInput(String userInput) {
        String[] inputParts = userInput.trim().split(RAW_COMMAND_DELIMIT, SPLIT_INPUT_LIMIT);
        switch (inputParts[0]) {
        case COMMAND_DONE:
            return splitInputToParts(inputParts, DELIMIT_DONE, COMMAND_DONE);
        case COMMAND_TODO:
            return splitInputToParts(inputParts, DELIMIT_TODO, COMMAND_TODO);
        case COMMAND_DEADLINE:
            return splitInputToParts(inputParts, DELIMIT_DEADLINE, COMMAND_DEADLINE);
        case COMMAND_EVENT:
            return splitInputToParts(inputParts, DELIMIT_EVENT, COMMAND_EVENT);
        default:
            //Catch List and Bye commands that need no processing
            return new String[]{ inputParts[0], INIT_STRING, INIT_STRING };
        }
    }

    /**
     * Returns the split inputParts into 3 distinct data: Command, duke.task.Task name, duke.task.Task Parameters
     *
     * @param inputParts Separated user input into 2 parts by the first spacing
     * @param splitBy The delimiter option to process the 2nd half of the user input
     * @param taskType The identified type of the COMMAND
     * @return processedInputs String array with the input in parts
     */
    private static String[] splitInputToParts(String[] inputParts, String splitBy, String taskType) {
        String userInput;
        String taskName = INIT_STRING;
        String taskParameter = INIT_STRING;
        try {
            taskName = inputParts[1];
            userInput = taskType;

            //Perform additional splicing if event types are deadline or event
            if (taskType.equals(COMMAND_DEADLINE) || taskType.equals(COMMAND_EVENT)) {
                String[] dateParts = inputParts[1].trim().split(splitBy, SPLIT_INPUT_LIMIT);
                taskName = dateParts[0];
                taskParameter = dateParts[1];
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            //Catches out of bounds for two different splicing
            System.out.println(ERROR_MESSAGE_NO_INFO);
            userInput = INVALID_OPTION;
        }
        return new String[]{ userInput, taskName, taskParameter };
    }

    /**
     * Returns True or False after it takes the processed input and executes the command.
     *
     * @param processedInputs An array of the processed user input
     * @return boolean Returns false if COMMAND_BYE to terminate the program
     */
    private static boolean executeCommand(String[] processedInputs) {
        String userInput = processedInputs[0];
        String taskName = processedInputs[1];
        String taskParameter = processedInputs[2];
        switch (userInput) {
        case COMMAND_BYE:
            printExitMessage();
            return false;
        case COMMAND_LIST:
            listAllTasks();
            break;
        case COMMAND_TODO:
            addTask(new Todo(taskName));
            saveTaskList();
            break;
        case COMMAND_DEADLINE:
            addTask(new Deadline(taskName, taskParameter));
            saveTaskList();
            break;
        case COMMAND_EVENT:
            addTask(new Event(taskName, taskParameter));
            saveTaskList();
            break;
        case COMMAND_DONE:
            completeTask(taskName);
            saveTaskList();
            break;
        default:
            printInvalidMessage();
            break;
        }
        return true;
    }

    private static void loadTaskList() {
        //Check data Folder exist
        File dataFolder = new File("data");
        File saveFile = new File("data/duke.txt");
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
            System.out.println("Data folder created!");
        }
        try {
            Scanner fileScanner = new Scanner(saveFile);
            while(fileScanner.hasNext()) {
                String currentLine = fileScanner.nextLine();
                String[] stringParts = currentLine.split("-", 5);
                switch (stringParts[1].trim()) {
                case "T":
                    Task loadTodo = new Todo(stringParts[2].trim());
                    if(stringParts[0].trim().equals("1")){
                        loadTodo.setCompleted(true);
                    }
                    tasks.add(loadTodo);
                    break;
                case "D":
                    Task loadDeadline = new Deadline(stringParts[2].trim(), stringParts[3].trim());
                    if(stringParts[0].trim().equals("1")){
                        loadDeadline.setCompleted(true);
                    }
                    tasks.add(loadDeadline);
                    break;
                case "E":
                    Task loadEvent = new Event(stringParts[2].trim(), stringParts[3].trim());
                    if(stringParts[0].trim().equals("1")){
                        loadEvent.setCompleted(true);
                    }
                    tasks.add(loadEvent);
                    break;
                default:
                    break;
                }
            }
            if (tasks.size() > 0) {
                System.out.println("Save file loaded! Added the following tasks:");
                listAllTasks();
            }
        } catch (java.io.FileNotFoundException notFoundExcept) {
            try {
                saveFile.createNewFile();
                System.out.println("Save file created!");
            } catch (java.io.IOException existExcept) {
                System.out.println("Save file already exists!");
            }
        }
    }

    private static void saveTaskList() {
        //Check data Folder exist
        File saveFile = new File("data/duke.txt");
        String saveString = INIT_STRING;
        for (Task saveTask: tasks) {
            if (saveTask.isCompleted()) {
                saveString += "1" + " - ";
            } else {
                saveString += "0" + " - ";
            }
            if (saveTask instanceof Todo) {
                saveString += "T" + " - ";
                saveString += saveTask.getTaskName() + " - ";
            } else if (saveTask instanceof Deadline) {
                saveString += "D" + " - ";
                saveString += saveTask.getTaskName() + " - ";
                saveString += ((Deadline) saveTask).getDueTime() + " - ";
            } else if (saveTask instanceof Event) {
                saveString += "E" + " - ";
                saveString += saveTask.getTaskName() + " - ";
                saveString += ((Event) saveTask).getDuration() + " - ";
            }
            saveString += System.lineSeparator();
        }
        try {
            FileWriter fw = new FileWriter(saveFile);
            fw.write(saveString);
            fw.close();
        } catch (IOException e) {
            System.out.println("Unable to write to file: " + e.getMessage());
        }
    }

    /**
     * Checks the task to complete, and completes it if it is a valid task.
     *
     * @param taskIDInString Processed input that identifies the task to complete
     */
    private static void completeTask(String taskIDInString) {
        // Change from String to Integer then 0-base to 1-base indexing by deducting 1
        int taskIDToFinish = Integer.parseInt(taskIDInString) - 1;
        if (taskIDToFinish < 0 || taskIDToFinish >= tasks.size()) {
            System.out.println(MESSAGE_NO_SUCH_TASK);
        } else if (Task.getTotalTasks() == 0) {
            System.out.println(MESSAGE_NO_REMAINING_TASKS);
        } else if (tasks.get(taskIDToFinish).isCompleted()) {
            System.out.println(MESSAGE_TASK_ALREADY_COMPLETED);
        } else {
            //Set the task to be completed and check remaining tasks.
            tasks.get(taskIDToFinish).setCompleted(true);
            System.out.println("'" + tasks.get(taskIDToFinish).getTaskName().trim() + "'");
            System.out.println(MESSAGE_TASK_COMPLETED);
            if(Task.getTotalTasks() == 0) {
                System.out.println(MESSAGE_ALL_TASK_COMPLETED);
            } else {
                System.out.println(MESSAGE_TASKS_LEFT + Task.getTotalTasks());
            }
        }
        System.out.println(SINGLE_LINE);
    }

    /**
     * Takes the new Tasks (Todos, Deadlines, Events) and adds it to the tasks ArrayList
     *
     * @param newTask Takes in as duke.task.Task object, but in reality it is a subclass.
     */
    private static void addTask(Task newTask) {
        tasks.add(newTask);
        System.out.println(SINGLE_LINE);
        System.out.println("+ " + newTask);
        System.out.println(MESSAGE_TASKS_LEFT + Task.getTotalTasks());
        System.out.println(SINGLE_LINE);
    }

    /**
     * Prints all Tasks stored in the tasks ArrayList
     */
    private static void listAllTasks() {
        if (tasks.size() == 0) {
            System.out.println(MESSAGE_NO_TASKS_DUE);
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ": " + tasks.get(i));
            }
        }
        System.out.println(SINGLE_LINE);
    }

    /**
     * Takes the raw user input from the next line
     *
     * @return the raw user input
     */
    private static String getUserInput() {
        return SCANNER.nextLine();
    }

    /**
     * Prints the Invalid Option message and a newline.
     */
    private static void printInvalidMessage() {
        System.out.println(MESSAGE_INVALID_OPTION);
    }

    /**
     * Prints the exit message
     */
    private static void printExitMessage() {
        System.out.println(SINGLE_LINE);
        System.out.println(MESSAGE_GOODBYE);
        System.out.println(SINGLE_LINE);
    }

    /**
     * Prints the welcome message
     */
    private static void printWelcomeScreen() {
        System.out.println(SINGLE_LINE);
        System.out.println(LOGO);
        System.out.println(MESSAGE_GREETING);
        System.out.println();
        System.out.println(SINGLE_LINE);
    }
}
