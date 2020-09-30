package duke.commands;

import duke.DukeException;
import duke.common.Messages;
import duke.storage.SaveManager;
import duke.task.TaskList;

/**
 * Deletes a task identified by a task id
 */
public class DeleteCommand extends Command {
    protected static final String NON_INTEGER_INPUT = "Please enter an integer";
    protected static final String INTEGER_ONLY_REGEX = "-?\\d+(\\.\\d+)?";
    private final int taskToDelete;

    public DeleteCommand(String taskToDelete) throws DukeException {
        //Input validation to handle non-integer input
        if (taskToDelete.matches(INTEGER_ONLY_REGEX)) {
            this.taskToDelete = Integer.parseInt(taskToDelete) - 1;
        } else {
            throw new DukeException(NON_INTEGER_INPUT);
        }
    }

    /**
     * Performs input validation and checks to delete only legitimate tasks
     *
     * @param tasks the TaskList object to delete the task from
     * @param saveManager the SaveManager object that will update the save file after deletion
     * @return ResultCommand object that has the result of the delete execution
     */
    @Override
    public ResultCommand execute(TaskList tasks, SaveManager saveManager) {
        String result;
        if (taskToDelete < 0 || taskToDelete >= tasks.getTasks().size()) {
            result = Messages.MESSAGE_NO_SUCH_TASK;
        } else {
            result = Messages.MESSAGE_REMOVED_TASK + System.lineSeparator();
            result += tasks.getTasks().get(taskToDelete);
            tasks.deleteTask(taskToDelete);
            saveManager.saveTaskList(tasks);
        }
        return new ResultCommand(result);
    }
}
