package duke.commands;

import duke.storage.SaveManager;
import duke.task.TaskList;

/**
 * Indicate to program to commence exit.
 */
public class ByeCommand extends Command {

    @Override
    public ResultCommand execute(TaskList tasks, SaveManager saveManager) {
        return new ResultCommand();
    }
}
