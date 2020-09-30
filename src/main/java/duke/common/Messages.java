package duke.common;

/**
 * Container for all messages used in program
 */
public class Messages {
    public static final String MESSAGE_NO_TASKS_DUE = "Fortunately, you have no tasks due";
    /**
     * Declare Messages to be used by Duke here.
     */
    public static final String MESSAGE_NO_SUCH_TASK = "That Task does not exist!";
    public static final String MESSAGE_NO_REMAINING_TASKS = "No remaining tasks";
    public static final String MESSAGE_TASK_ALREADY_COMPLETED = "That Task has already " +
            "been completed, but let's shoot it again";
    public static final String MESSAGE_TASK_COMPLETED = "marked as completed, well done!";
    public static final String MESSAGE_ALL_TASK_COMPLETED = "All Task Completed!";
    public static final String MESSAGE_TASKS_LEFT = "You a total of tasks remaining: ";
    public static final String MESSAGE_GOODBYE = "Bye! Hope to see you again soon!";
    public static final String MESSAGE_GREETING = "Hello! I'm Duke!" + System.lineSeparator()
                    + "What can I do for you?";
    public static final String ERROR_MESSAGE_NO_INFO = "Please provide more information!";
    public static final String MESSAGE_REMOVED_TASK = "Noted! I have removed the task:";
    public static final String ERROR_CANNOT_WRITE = "Unable to write to file: ";
    public static final String MESSAGE_SUCCESSFUL_LOAD = "Save file loaded! Added the following tasks:";
    public static final String MESSAGE_NO_TASK_LOADED = "No tasks loaded!";
    public static final String ERROR_DUPLICATE_SAVE = "Save file already exists!";
    public static final String MESSAGE_CREATED_SAVE_FILE = "Save file not found! New file created!";

    public static final String LOGO = " ____        _" + System.lineSeparator()
            + "|  _ \\ _   _| | _____" + System.lineSeparator()
            + "| | | | | | | |/ / _ \\" + System.lineSeparator()
            + "| |_| | |_| |   <  __/" + System.lineSeparator()
            + "|____/ \\__,_|_|\\_\\___|" + System.lineSeparator();
    public static final String SINGLE_LINE =
                    "____________________________________________________________";
    public static final String INIT_STRING = "";
}
