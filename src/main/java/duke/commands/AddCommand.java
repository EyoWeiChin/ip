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


public class AddCommand extends Command {

    private static final String TYPE_DEADLINE = "deadline";
    private static final String TYPE_EVENT = "event";
    private static final String ADD_SYMBOL = "+ ";
    private Task addThis;
    private String result;

    public AddCommand(String taskName) {
        addThis = new Todo(taskName);
    }

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

    @Override
    public ResultCommand execute(TaskList tasks, SaveManager saveManager) {
        tasks.addTask(addThis);
        saveManager.saveTaskList(tasks);
        result = Messages.SINGLE_LINE + System.lineSeparator() + ADD_SYMBOL + addThis + System.lineSeparator();
        result += Messages.MESSAGE_TASKS_LEFT + Task.getTotalTasks();
        return new ResultCommand(result);
    }
}
