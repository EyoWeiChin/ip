package duke.commands;

import duke.storage.SaveManager;
import duke.task.TaskList;

public class Command {
    public ResultCommand execute(TaskList tasks, SaveManager saveManager) {
        return new ResultCommand();
    }

}
