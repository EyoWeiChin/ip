package duke.commands;

import duke.common.Messages;
import duke.storage.SaveManager;
import duke.task.Task;
import duke.task.TaskList;

/**
 * Mark a Task as completed indicated by an index
 */
public class DoneCommand extends Command {
    private String taskIDInString;
    private String result;

    public DoneCommand(String taskIDInString) {
        this.taskIDInString = taskIDInString;
    }

    /**
     * Performs input validation and checks to ensure that task to be completed is a legitmate task before
     * completing it
     * @param tasks TaskList to complete the task
     * @param saveManager Updates this save file after completion
     * @return ResultCommand object that has the result of the done execution
     */
    @Override
    public ResultCommand execute(TaskList tasks, SaveManager saveManager) {
        int taskIDToFinish = Integer.parseInt(taskIDInString) - 1;
        if (taskIDToFinish < 0 || taskIDToFinish >= tasks.getTasks().size()) {
            result = Messages.MESSAGE_NO_SUCH_TASK;
        } else if (Task.getTotalTasks() == 0) {
            result = Messages.MESSAGE_NO_REMAINING_TASKS;
        } else if (tasks.getTasks().get(taskIDToFinish).isCompleted()) {
            result = Messages.MESSAGE_TASK_ALREADY_COMPLETED;
        } else {
            tasks.completeTask(taskIDToFinish);
            saveManager.saveTaskList(tasks);
            result = "'" + tasks.getTasks().get(taskIDToFinish).getTaskName().trim() + "'";
            result += System.lineSeparator() + Messages.MESSAGE_TASK_COMPLETED;
            if (Task.getTotalTasks() == 0) {
                result += Messages.MESSAGE_ALL_TASK_COMPLETED;
            } else {
                result += Messages.MESSAGE_TASKS_LEFT + Task.getTotalTasks();
            }
        }
        return new ResultCommand(result);

    }
}
