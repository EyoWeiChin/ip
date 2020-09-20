package duke.commands;

import duke.common.Messages;
import duke.storage.SaveManager;
import duke.task.TaskList;

/**
 * Deletes a task identified by a task id
 */
public class DeleteCommand extends Command {
    private String taskToDelete;
    private String result;

    public DeleteCommand(String taskToDelete) {
        this.taskToDelete = taskToDelete;
    }

    /**
     * Performs input validation and checks to delete only legitimate tasks
     * @param tasks TaskList to delete from
     * @param saveManager Updates this save file after delete
     * @return ResultCommand object that has the result of the delete execution
     */
    @Override
    public ResultCommand execute(TaskList tasks, SaveManager saveManager) {
        int taskToDeleteInt = Integer.parseInt(taskToDelete) - 1;
        if (taskToDeleteInt < 0 || taskToDeleteInt >= tasks.getTasks().size()) {
            result = Messages.MESSAGE_NO_SUCH_TASK;
        } else {
            result = Messages.MESSAGE_REMOVED_TASK + System.lineSeparator();
            result += tasks.getTasks().get(taskToDeleteInt);
            tasks.deleteTask(taskToDeleteInt);
            saveManager.saveTaskList(tasks);
        }
        return new ResultCommand(result);
    }
}
