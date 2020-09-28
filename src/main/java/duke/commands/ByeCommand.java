package duke.commands;

import duke.storage.SaveManager;
import duke.task.TaskList;

/**
 * Indicates an exit command and program will commence exit.
 */
public class ByeCommand extends Command {

    @Override
    public ResultCommand execute(TaskList tasks, SaveManager saveManager) {
        return new ResultCommand();
    }
}
