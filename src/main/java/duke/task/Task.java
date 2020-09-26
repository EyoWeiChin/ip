package duke.task;

/**
 * Represents a Task in a TaskList
 */
public class Task {
    private static final String NO_DUE_DATE = "";
    protected static final String DONE_ICON = "[✓]";
    protected static final String NOT_DONE_ICON = "[✗]";
    protected static final String WHITE_SPACE = " ";
    private boolean isCompleted;
    private String taskName;
    private static int totalTasks = 0;

    public Task(String taskName) {
        this.isCompleted = false;
        this.taskName = taskName;
        totalTasks += 1;
    }

    public String getDueTime() {
        return NO_DUE_DATE;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public String getStatusIndicator() {
        return (isCompleted ? DONE_ICON : NOT_DONE_ICON);
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
        totalTasks -= 1;
    }

    public String getTaskName() {
        return taskName;
    }

    public static int getTotalTasks() {
        return totalTasks;
    }

    @Override
    public String toString() {
        return getStatusIndicator() + WHITE_SPACE + getTaskName();
    }
}
