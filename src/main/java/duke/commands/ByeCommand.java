package duke.commands;

import duke.storage.SaveManager;
import duke.task.TaskList;

public class ByeCommand extends Command {

    @Override
    public ResultCommand execute(TaskList tasks, SaveManager saveManager) {
        return new ResultCommand();
    }
}
