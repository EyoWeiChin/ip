package duke.task;

public class Deadline extends Task {
    protected String dueTime;

    public Deadline(String taskName, String dueTime) {
        super(taskName);
        this.dueTime = dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    @Override
    public String getDueTime() {
        return dueTime;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by:" + dueTime + ")";
    }
}
