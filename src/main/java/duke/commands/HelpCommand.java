package duke.commands;

import duke.storage.SaveManager;
import duke.task.TaskList;

public class HelpCommand extends Command {
    protected static final String HELP_HEADER_MESSAGE = "Duke.java v0.2 Commands";

    protected static final String NEW_LINE = System.lineSeparator();
    protected static final String TWO_NEW_LINE = System.lineSeparator() + System.lineSeparator();

    protected static final String LIST_COMMAND = "'list'";
    protected static final String LIST_DESCRIPTION = "List all the task added";

    protected static final String TODO_COMMAND = "'todo task_name'";
    protected static final String TODO_DESCRIPTION = "Adds a todo task";

    protected static final String DEADLINE_COMMAND = "'deadline task_name /by YYYY-MM-DD HHmm'";
    protected static final String DEADLINE_DESCRIPTION = "Adds a deadline task";

    protected static final String EVENT_COMMAND = "'event task_name /at YYYY-MM-DD HHmm'";
    protected static final String EVENT_DESCRIPTION = "Adds an event task";

    protected static final String DONE_COMMAND = "'done task_id'";
    protected static final String DONE_DESCRIPTION = "Completes a task";

    protected static final String DELETE_COMMAND = "'delete task_id'";
    protected static final String DELETE_DESCRIPTION = "Deletes any task";

    protected static final String FIND_COMMAND = "'find task_name'";
    protected static final String FIND_DESCRIPTION = "Finds a task with task_name string";

    protected static final String DUE_COMMAND = "'due YYYY-MM-DD'";
    protected static final String DUE_DESCRIPTION = "Finds task that has the specified due date";

    protected static final String BYE_COMMAND = "'bye'";
    protected static final String EXIT_DESCRIPTION = "Exit the program";
    protected static final String USER_GUIDE_LINK = "View the User Guide: https://eyoweichin.github.io/ip/";

    @Override
    public ResultCommand execute(TaskList tasks, SaveManager saveManager) {
        String result = HELP_HEADER_MESSAGE + NEW_LINE;
        result += LIST_COMMAND + NEW_LINE + LIST_DESCRIPTION + TWO_NEW_LINE;
        result += TODO_COMMAND + NEW_LINE + TODO_DESCRIPTION + TWO_NEW_LINE;
        result += DEADLINE_COMMAND + NEW_LINE + DEADLINE_DESCRIPTION + TWO_NEW_LINE;
        result += EVENT_COMMAND + NEW_LINE + EVENT_DESCRIPTION + TWO_NEW_LINE;
        result += DONE_COMMAND + NEW_LINE + DONE_DESCRIPTION + TWO_NEW_LINE;
        result += DELETE_COMMAND + NEW_LINE + DELETE_DESCRIPTION + TWO_NEW_LINE;
        result += FIND_COMMAND + NEW_LINE + FIND_DESCRIPTION + TWO_NEW_LINE;
        result += DUE_COMMAND + NEW_LINE + DUE_DESCRIPTION + TWO_NEW_LINE;
        result += BYE_COMMAND + NEW_LINE + EXIT_DESCRIPTION + NEW_LINE;
        result += USER_GUIDE_LINK;
        return new ResultCommand(result);
    }
}
