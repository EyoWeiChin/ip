package duke.task;

/**
 * Represents an Event task
 */
public class Event extends Task {
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
        return "[E]" + super.toString() + " (at:" + duration + ")";
    }
}
