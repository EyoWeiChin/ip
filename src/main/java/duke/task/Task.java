package duke.task;

public class Task {
    private static final String NO_DUE_DATE = "";
    //duke.task.Task attribute
    private boolean isCompleted;
    private String taskName;

    //Class Level Attribute
    private static int totalTasks = 0;

    //duke.task.Task Constructor
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
        return (isCompleted ? "[✓]" : "[✗]");
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
        totalTasks -= 1;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public static int getTotalTasks() {
        return totalTasks;
    }

    public static void setTotalTasks(int totalTasks) {
        Task.totalTasks = totalTasks;
    }

    @Override
    public String toString() {
        return getStatusIndicator() + " " + getTaskName();
    }
}
