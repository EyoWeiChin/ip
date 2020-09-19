package duke.commands;

import duke.storage.SaveManager;
import duke.task.TaskList;

public class ListCommand extends Command {

    @Override
    public ResultCommand execute(TaskList tasks, SaveManager saveManager) {
        tasks.listAllTasks();
        return new ResultCommand();
    }
}
