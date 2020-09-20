package duke.commands;

import duke.storage.SaveManager;
import duke.task.TaskList;

/**
 * Finds a task using the task's name and a search string
 */
public class FindCommand extends Command {
    private String findThisTask;

    public FindCommand(String findThisTask) {
        this.findThisTask = findThisTask;
    }

    @Override
    public ResultCommand execute(TaskList tasks, SaveManager saveManager) {
        tasks.getTasks().stream().filter((s)-> s.getTaskName().contains(findThisTask))
                .forEach(System.out::println);
        return new ResultCommand();
    }
}
