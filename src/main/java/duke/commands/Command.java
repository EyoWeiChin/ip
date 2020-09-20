package duke.commands;

import duke.storage.SaveManager;
import duke.task.TaskList;

/**
 * Abstract class that represent an executable command
 */
public abstract class Command {
    public abstract ResultCommand execute(TaskList tasks, SaveManager saveManager);

}
