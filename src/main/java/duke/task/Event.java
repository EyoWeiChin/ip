package duke.task;

public class Event extends Task {
    protected String duration;

    public Event(String taskName, String duration) {
        super(taskName);
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at:" + duration + ")";
    }
}
