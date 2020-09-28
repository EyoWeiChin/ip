package duke.commands;

import duke.DukeException;
import duke.common.Messages;
import duke.storage.SaveManager;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.TaskList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Searches and returns for all tasks with the specified due date.
 */
public class DueCommand extends Command {

    private static final String ERROR_INVALID_DATE = "Invalid Date! Format: 'due YYYY/MM/DD'";
    private LocalDate checkThisDate;

    /**
     * Constructor that validates the specified date input
     *
     * @param checkThisDate The specified date to search for
     * @throws DukeException if the date is not in the valid format
     */
    public DueCommand(String checkThisDate) throws DukeException {
        try {
            this.checkThisDate = LocalDate.parse(checkThisDate.trim());
        } catch(java.time.format.DateTimeParseException invalidDateFormat) {
            throw new DukeException(ERROR_INVALID_DATE);
        }
    }

    /**
     * Finds all Deadline and Event tasks that has the same specified LocalDate Object.
     *
     * @param tasks The TaskList object to complete the task
     * @param saveManager Updates this save file after completion
     * @return ResultCommand object that has the found tasks as result
     */
    @Override
    public ResultCommand execute(TaskList tasks, SaveManager saveManager) {
        String formattedDate = checkThisDate.format(DateTimeFormatter.ofPattern(Messages.DATE_TIME_FORMAT));

        //Used stream to find Deadline and Event tasks that has the request date, then it prints them.
        tasks.getTasks().stream().filter((s)-> s instanceof Deadline || s instanceof Event)
                .filter((s)->s.getDueTime().equals(formattedDate.trim()))
                .forEach(System.out::println);
        return new ResultCommand();
    }
}
