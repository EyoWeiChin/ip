package duke.task;

/**
 * Represents a Deadline task
 */
public class Deadline extends Task {
    protected static final String DEADLINE_ICON = "[D]";
    protected static final String DURATION_OPEN = " (by:";
    protected static final String DURATION_CLOSE = ")";
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
        return DEADLINE_ICON + super.toString() + DURATION_OPEN + dueTime + DURATION_CLOSE;
    }
}
