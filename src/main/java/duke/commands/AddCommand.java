package duke.commands;

import duke.common.Messages;
import duke.storage.SaveManager;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.Todo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Adds a task to TaskList
 */
public class AddCommand extends Command {

    private static final String TYPE_DEADLINE = "deadline";
    private static final String TYPE_EVENT = "event";
    private static final String ADD_SYMBOL = "+ ";
    protected static final String INVALID_DATE_FORMAT = "Not a valid date format. Storing as String instead!";
    protected static final String EXPECTED_DATE_FORMAT = "yyyy-MM-dd HHmm";
    protected static final String PRINTED_DATE_FORMAT = "dd/MMM/yyyy hh:mm a";
    private Task addThis;
    private String result = "";

    /**
     * Constructor for Todo tasks
     * @param taskName Name of the task
     */
    public AddCommand(String taskName) {
        addThis = new Todo(taskName);
    }

    /**
     * Constructor to handle Deadline and Event tasks
     *
     * @param taskName Name of the task
     * @param taskDuration Duration of the the tasks, it could be a LocalDate object in String form.
     * @param taskType The type of task. It will be either a deadline or event identifier.
     */
    public AddCommand(String taskName, String taskDuration, String taskType) {
        taskDuration = checkAndParseDate(taskDuration);
        if(taskType.equals(TYPE_DEADLINE)) {
            addThis = new Deadline(taskName, taskDuration);
        } else if(taskType.equals(TYPE_EVENT)) {
            addThis = new Event(taskName, taskDuration);
        }
    }

    /**
     * Checks if the user input a valid date and convert it to readable format
     *
     * @param taskDuration the user input for duration
     * @return a String that has the readable date format or normal user input string if not valid date
     */
    private String checkAndParseDate(String taskDuration) {
        try {
            SimpleDateFormat stringToDate = new SimpleDateFormat(EXPECTED_DATE_FORMAT);
            Date taskDate = stringToDate.parse(taskDuration.trim());
            SimpleDateFormat newDateFormat = new SimpleDateFormat(PRINTED_DATE_FORMAT);
            taskDuration = newDateFormat.format(taskDate);
        } catch (java.text.ParseException invalidDateFormat) {
            result = INVALID_DATE_FORMAT + System.lineSeparator();
        }
        return taskDuration;
    }

    /**
     * Executes the command by adding to TaskList and saving the update to the save file
     *
     * @param tasks the TaskList object that the task will be added to.
     * @param saveManager the SaveManager object.
     * @return ResultCommand object that will contain the output of the command.
     */
    @Override
    public ResultCommand execute(TaskList tasks, SaveManager saveManager) {
        tasks.addTask(addThis);
        saveManager.saveTaskList(tasks);
        result += Messages.SINGLE_LINE + System.lineSeparator() + ADD_SYMBOL + addThis + System.lineSeparator();
        result += Messages.MESSAGE_TASKS_LEFT + Task.getTotalTasks();
        return new ResultCommand(result);
    }
}
