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

    /**
     * Finds tasks that has the search string in the task name.
     *
     * @param tasks the TaskList object
     * @param saveManager the SaveManager object
     * @return
     */
    @Override
    public ResultCommand execute(TaskList tasks, SaveManager saveManager) {
        //Stream that will find Tasks that has the search string and then prints them.
        tasks.getTasks().stream().filter((s)-> s.getTaskName().contains(findThisTask))
                .forEach(System.out::println);
        return new ResultCommand();
    }
}
