package duke.commands;

import duke.common.Messages;
import duke.storage.SaveManager;
import duke.task.TaskList;

public class DeleteCommand extends Command {
    private String taskToDelete;
    private String result;

    public DeleteCommand(String taskToDelete) {
        this.taskToDelete = taskToDelete;
    }

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
