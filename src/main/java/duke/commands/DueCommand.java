package duke.commands;

import duke.DukeException;
import duke.common.Messages;
import duke.storage.SaveManager;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.TaskList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DueCommand extends Command {

    private static final String ERROR_INVALID_DATE = "Invalid Date!";
    private LocalDate checkThisDate;

    public DueCommand(String checkThisDate) throws DukeException {
        try {
            this.checkThisDate = LocalDate.parse(checkThisDate.trim());
        } catch(java.time.format.DateTimeParseException invalidDateFormat) {
            throw new DukeException(ERROR_INVALID_DATE);
        }
    }

    @Override
    public ResultCommand execute(TaskList tasks, SaveManager saveManager) {
        String formattedDate = checkThisDate.format(DateTimeFormatter.ofPattern(Messages.DATE_TIME_FORMAT));
        tasks.getTasks().stream().filter((s)-> s instanceof Deadline || s instanceof Event)
                .filter((s)->s.getDueTime().equals(formattedDate.trim()))
                .forEach(System.out::println);
        return new ResultCommand();
    }
}
