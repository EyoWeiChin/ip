package duke.commands;

import duke.storage.SaveManager;
import duke.task.TaskList;

/**
 * Lists all tasks in the task lists
 */
public class ListCommand extends Command {

    @Override
    public ResultCommand execute(TaskList tasks, SaveManager saveManager) {
        tasks.listAllTasks();
        return new ResultCommand();
    }
}
