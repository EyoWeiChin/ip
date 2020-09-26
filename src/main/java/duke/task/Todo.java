package duke.task;

/**
 * Represents a Todo Task
 */
public class Todo extends Task {
    protected static final String TODO_ICON = "[T]";

    public Todo(String taskName) {
        super(taskName);
    }

    @Override
    public String toString() {
        return TODO_ICON + super.toString();
    }
}
