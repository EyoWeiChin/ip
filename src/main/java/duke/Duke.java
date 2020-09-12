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

    //Constant Variables for Save / Load logic
    private static final String DELIMIT_SAVE_FILE = " | ";
    private static final String DELIMIT_SAVE_FILE_REGEX = "\\|";
    private static final int SPLIT_SAVE_LIMIT = 5;

    private static final String SAVE_TODO = "T";
    private static final String SAVE_DEADLINE = "D";
    private static final String SAVE_EVENT = "E";

    private static final String SAVE_COMPLETED_TASK = "1";
    private static final String SAVE_NOT_COMPLETED_TASK = "0";

    /**
     * ArrayList of all Tasks
     */
    private static final ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Declare scanner to read from I/O
     */
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Declare file paths for saving and loading
     */
    private static final File SAVE_FILE = new File("data/duke.txt");
    private static final File DATA_FOLDER = new File("data");

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
    private static final String ERROR_CANNOT_WRITE = "Unable to write to file: ";
    private static final String MESSAGE_CREATED_FOLDER = "Data folder created!";
    private static final String MESSAGE_SUCCESSFUL_LOAD = "Save file loaded! Added the following tasks:";
    private static final String ERROR_DUPLICATE_SAVE = "Save file already exists!";
    private static final String MESSAGE_CREATED_SAVE_FILE = "Save file created!";


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

    /**
     * Reads the saved file and adds the saved data to the data structure.
     *
     * @throws java.io.FileNotFoundException if SAVE_FILE does not exist
     */
    private static void savedFileReader() throws java.io.FileNotFoundException {
        Scanner fileScanner = new Scanner(SAVE_FILE);
        while(fileScanner.hasNext()) {
            String currentLine = fileScanner.nextLine();
            String[] stringParts = currentLine.split(DELIMIT_SAVE_FILE_REGEX, SPLIT_SAVE_LIMIT);
            switch (stringParts[1].trim()) {
            case SAVE_TODO:
                Task loadTodo = new Todo(stringParts[2].trim());
                if (stringParts[0].trim().equals(SAVE_COMPLETED_TASK)) {
                    loadTodo.setCompleted(true);
                }
                tasks.add(loadTodo);
                break;
            case SAVE_DEADLINE:
                Task loadDeadline = new Deadline(stringParts[2].trim(), stringParts[3].trim());
                if (stringParts[0].trim().equals(SAVE_COMPLETED_TASK)) {
                    loadDeadline.setCompleted(true);
                }
                tasks.add(loadDeadline);
                break;
            case SAVE_EVENT:
                Task loadEvent = new Event(stringParts[2].trim(), stringParts[3].trim());
                if (stringParts[0].trim().equals(SAVE_COMPLETED_TASK)) {
                    loadEvent.setCompleted(true);
                }
                tasks.add(loadEvent);
                break;
            default:
                break;
            }
        }
    }

    /**
     * Checks if save folder and save file exists, if not create them.
     * If they exist, call savedFileReader() to read it.
     */
    private static void loadTaskList() {
        if (!DATA_FOLDER.exists()) {
            DATA_FOLDER.mkdir();
            System.out.println(MESSAGE_CREATED_FOLDER);
        }
        try {
            savedFileReader();
        } catch (java.io.FileNotFoundException notFoundExcept) {
            try {
                //Create the save file if exception was thrown
                SAVE_FILE.createNewFile();
                System.out.println(MESSAGE_CREATED_SAVE_FILE);
            } catch (java.io.IOException existExcept) {
                System.out.println(ERROR_DUPLICATE_SAVE + existExcept);
            }
        }
        //Print the added task if any
        if (tasks.size() > 0) {
            System.out.println(MESSAGE_SUCCESSFUL_LOAD);
            listAllTasks();
        }
    }

    private static void saveTaskList() {
        StringBuilder saveString = new StringBuilder(INIT_STRING);
        //Loop through the Task ArrayList and build the string to save
        for (Task saveTask: tasks) {
            if (saveTask.isCompleted()) {
                saveString.append(SAVE_COMPLETED_TASK + DELIMIT_SAVE_FILE);
            } else {
                saveString.append(SAVE_NOT_COMPLETED_TASK + DELIMIT_SAVE_FILE);
            }
            if (saveTask instanceof Todo) {
                saveString.append(SAVE_TODO + DELIMIT_SAVE_FILE);
                saveString.append(saveTask.getTaskName()).append(DELIMIT_SAVE_FILE);
            } else if (saveTask instanceof Deadline) {
                saveString.append(SAVE_DEADLINE + DELIMIT_SAVE_FILE);
                saveString.append(saveTask.getTaskName()).append(DELIMIT_SAVE_FILE);
                saveString.append(((Deadline) saveTask).getDueTime()).append(DELIMIT_SAVE_FILE);
            } else if (saveTask instanceof Event) {
                saveString.append(SAVE_EVENT + DELIMIT_SAVE_FILE);
                saveString.append(saveTask.getTaskName()).append(DELIMIT_SAVE_FILE);
                saveString.append(((Event) saveTask).getDuration()).append(DELIMIT_SAVE_FILE);
            }
            saveString.append(System.lineSeparator());
        }
        try {
            FileWriter fw = new FileWriter(SAVE_FILE);
            fw.write(saveString.toString());
            fw.close();
        } catch (IOException e) {
            System.out.println(ERROR_CANNOT_WRITE + e.getMessage());
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
