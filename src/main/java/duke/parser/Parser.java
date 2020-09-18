package duke.parser;

import duke.DukeException;
import duke.common.Messages;
import duke.storage.SaveManager;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.TaskList;
import duke.task.Todo;

import java.util.Scanner;

public class Parser {

    //Constant variables for Command logic
    protected static final String RAW_COMMAND_DELIMIT = " ";
    protected static final int SPLIT_INPUT_LIMIT = 2;
    protected static final String COMMAND_DELETE = "delete";
    protected static final String DELIMIT_DELETE = "";
    protected static final String COMMAND_TODO = "todo";
    protected static final String DELIMIT_TODO = "";
    protected static final String COMMAND_DONE = "done";
    protected static final String DELIMIT_DONE = "";
    protected static final String COMMAND_DEADLINE = "deadline";
    protected static final String DELIMIT_DEADLINE = "/by";
    protected static final String COMMAND_EVENT = "event";
    protected static final String DELIMIT_EVENT = "/at";
    protected static final String COMMAND_LIST = "list";
    protected static final String COMMAND_BYE = "bye";
    protected static final String INVALID_OPTION = "invalid";

    /**
     * Declare scanner to read from I/O
     */
    public static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Returns the split inputParts into 3 distinct data: Command, Task name, Task Parameters
     *
     * @param inputParts Separated user input into 2 parts by the first spacing
     * @param splitBy The delimiter option to process the 2nd half of the user input
     * @param taskType The identified type of the COMMAND
     * @return processedInputs String array with the input in parts
     */
    public static String[] splitInputToParts(String[] inputParts, String splitBy, String taskType) {
        String userInput;
        String taskName = Messages.INIT_STRING;
        String taskParameter = Messages.INIT_STRING;
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
            System.out.println(Messages.ERROR_MESSAGE_NO_INFO);
            userInput = INVALID_OPTION;
        }
        return new String[]{ userInput, taskName, taskParameter };
    }

    /**
     * Returns the raw user input into processed parts for execution based on found keywords
     *
     *
     * @return processedInputs String array with the input in parts as follows
     * processedInputs[0] the processed Command e.g COMMAND_DONE, COMMAND_TODO, ...
     * processedInputs[1] taskName e.g 'borrow book' or 1 (taskID for done commands)
     * processedInputs[2] = taskParameter, e.g 'Sunday', 'Mon 2pm-4pm'
     */
    public static String[] processInput() {
        String userInput = getUserInput();
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
        case COMMAND_DELETE:
            return splitInputToParts(inputParts, DELIMIT_DELETE, COMMAND_DELETE);
        default:
            //Catch List and Bye commands that need no processing
            return new String[]{ inputParts[0], Messages.INIT_STRING, Messages.INIT_STRING };
        }
    }

    /**
     * Takes the raw user input from the next line
     *
     * @return the raw user input
     */
    public static String getUserInput() {
        return SCANNER.nextLine();
    }

    /**
     * Returns True or False after it takes the processed input and executes the command.
     *
     * @param processedInputs An array of the processed user input
     * @return boolean Returns false if COMMAND_BYE to terminate the program
     */
    public static boolean executeCommand(String[] processedInputs, TaskList tasks) throws DukeException {
        String userInput = processedInputs[0];
        String taskName = processedInputs[1];
        String taskParameter = processedInputs[2];
        switch (userInput) {
        case COMMAND_BYE:
            return false;
        case COMMAND_LIST:
            TaskList.listAllTasks();
            break;
        case COMMAND_TODO:
            TaskList.addTask(new Todo(taskName));
            SaveManager.saveTaskList(tasks);
            break;
        case COMMAND_DEADLINE:
            TaskList.addTask(new Deadline(taskName, taskParameter));
            SaveManager.saveTaskList(tasks);
            break;
        case COMMAND_EVENT:
            TaskList.addTask(new Event(taskName, taskParameter));
            SaveManager.saveTaskList(tasks);
            break;
        case COMMAND_DONE:
            TaskList.completeTask(taskName);
            SaveManager.saveTaskList(tasks);
            break;
        case COMMAND_DELETE:
            TaskList.deleteTask(taskName);
            SaveManager.saveTaskList(tasks);
            break;
        default:
            throw new DukeException(Messages.MESSAGE_INVALID_OPTION);
        }
        return true;
    }
}
