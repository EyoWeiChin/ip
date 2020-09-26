package duke.task;

/**
 * Represents an Event task
 */
public class Event extends Task {
    protected static final String EVENT_ICON = "[E]";
    protected static final String DURATION_OPEN = " (at:";
    protected static final String DURATION_CLOSE = ")";
    protected String duration;

    public Event(String taskName, String duration) {
        super(taskName);
        this.duration = duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String getDueTime() {
        return duration;
    }

    @Override
    public String toString() {
        return EVENT_ICON + super.toString() + DURATION_OPEN + duration + DURATION_CLOSE;
    }
}
