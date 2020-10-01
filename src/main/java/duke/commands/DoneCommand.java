package duke.commands;

import duke.DukeException;
import duke.common.Messages;
import duke.storage.SaveManager;
import duke.task.Task;
import duke.task.TaskList;

/**
 * Mark a Task as completed indicated by an index
 */
public class DoneCommand extends Command {
    private int taskIDToComplete;
    private String result;
    protected static final String NON_INTEGER_INPUT = "Please enter an integer";
    protected static final String INTEGER_ONLY_REGEX = "-?\\d+(\\.\\d+)?";

    /**
     * Checks if the String is an integer value and parses it as an int
     *
     * @param taskIDInString the task index to delete as String
     * @throws DukeException If a taskIDInString is not an int
     */
    public DoneCommand(String taskIDInString) throws DukeException {
        if (taskIDInString.matches(INTEGER_ONLY_REGEX)) {
            this.taskIDToComplete = Integer.parseInt(taskIDInString) - 1;
        } else {
            throw new DukeException(NON_INTEGER_INPUT);
        }
    }

    /**
     * Completes the tasks if it is a valid Task that can be completed
     *
     * @param tasks TaskList to complete the task
     * @param saveManager Updates this save file after completion
     * @return ResultCommand object that has the result of the done execution
     */
    @Override
    public ResultCommand execute(TaskList tasks, SaveManager saveManager) {
        if (canTaskBeCompleted(tasks)) {
            tasks.completeTask(taskIDToComplete);
            saveManager.saveTaskList(tasks);
        }
        return new ResultCommand(result);
    }

    /**
     * Checks if the task is a valid task or is not completed yet.
     *
     * @param tasks the Tasklist object which the task to check resides in
     * @return boolean whether this task is completable or not
     */

    private boolean canTaskBeCompleted(TaskList tasks) {
        boolean isValidTask = false;
        if (taskIDToComplete < 0 || taskIDToComplete >= tasks.getTasks().size()) {
            result = Messages.MESSAGE_NO_SUCH_TASK;
        } else if (Task.getTotalTasks() == 0) {
            result = Messages.MESSAGE_NO_REMAINING_TASKS;
        } else if (tasks.getTasks().get(taskIDToComplete).isCompleted()) {
            result = Messages.MESSAGE_TASK_ALREADY_COMPLETED;
        } else {
            result = "'" + tasks.getTasks().get(taskIDToComplete).getTaskName().trim() + "'";
            result += System.lineSeparator() + Messages.MESSAGE_TASK_COMPLETED + System.lineSeparator();
            if (Task.getTotalTasks() == 0) {
                result += Messages.MESSAGE_ALL_TASK_COMPLETED;
            } else {
                result += Messages.MESSAGE_TASKS_LEFT + Task.getTotalTasks();
            }
            isValidTask = true;
        }
        return isValidTask;
    }
}