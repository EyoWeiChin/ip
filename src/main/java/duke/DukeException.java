package duke;

/**
 * Represents an Exception unique to Duke
 */
public class DukeException extends Exception {
    protected static final String DEFAULT_ERROR_HEADER = "Error: ";
    protected String message;

    public DukeException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return DEFAULT_ERROR_HEADER + this.message;
    }
}
