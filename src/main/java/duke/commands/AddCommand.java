package duke.commands;

import duke.common.Messages;
import duke.storage.SaveManager;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.Todo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Adds a task to TaskList
 */
public class AddCommand extends Command {

    private static final String TYPE_DEADLINE = "deadline";
    private static final String TYPE_EVENT = "event";
    private static final String ADD_SYMBOL = "+ ";
    private Task addThis;
    private String result;

    /**
     * Constructor for Todo tasks
     * @param taskName Name of the task
     */
    public AddCommand(String taskName) {
        addThis = new Todo(taskName);
    }

    /**
     * Constructor to handle Deadline and Event tasks
     * @param taskName Name of the task
     * @param taskDuration Received as String but could potentially be LocalDate object.
     * @param taskType Will be either a deadline or event identifier.
     */
    public AddCommand(String taskName, String taskDuration, String taskType) {
        try {
            LocalDate tryParsing = LocalDate.parse(taskDuration.trim());
            taskDuration = tryParsing.format(DateTimeFormatter.ofPattern(Messages.DATE_TIME_FORMAT));
        } catch(java.time.format.DateTimeParseException invalidDateFormat) {
            //Do nothing to modify the taskDuration. This enables us to store other strings as task Duration
        }

        if(taskType.equals(TYPE_DEADLINE)) {
            addThis = new Deadline(taskName, taskDuration);
        } else if(taskType.equals(TYPE_EVENT)) {
            addThis = new Event(taskName, taskDuration);
        }
    }

    /**
     * Executes the command by adding to TaskList and saving the update to the save file
     * @param tasks This is the TaskList to add to.
     * @param saveManager This is the SaveManager class.
     * @return ResultCommand object that will store the output to show to user.
     */
    @Override
    public ResultCommand execute(TaskList tasks, SaveManager saveManager) {
        tasks.addTask(addThis);
        saveManager.saveTaskList(tasks);
        result = Messages.SINGLE_LINE + System.lineSeparator() + ADD_SYMBOL + addThis + System.lineSeparator();
        result += Messages.MESSAGE_TASKS_LEFT + Task.getTotalTasks();
        return new ResultCommand(result);
    }
}
