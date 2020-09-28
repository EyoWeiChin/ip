package duke.parser;

import duke.DukeException;
import duke.commands.AddCommand;
import duke.commands.ByeCommand;
import duke.commands.Command;
import duke.commands.DeleteCommand;
import duke.commands.DoneCommand;
import duke.commands.DueCommand;
import duke.commands.FindCommand;
import duke.commands.ListCommand;
import duke.common.Messages;

/**
 * Parses and handles user input
 */
public class Parser {

    /**
     * Constant variables used for input and string processing
     */
    protected static final String RAW_COMMAND_DELIMIT = " ";
    protected static final int SPLIT_INPUT_LIMIT = 2;
    protected static final String COMMAND_DELETE = "delete";
    protected static final String DELIMIT_DELETE = "";

    protected static final String COMMAND_TODO = "todo";
    protected static final String DELIMIT_TODO = "";

    protected static final String COMMAND_DONE = "done";
    protected static final String DELIMIT_DONE = "";

    protected static final String COMMAND_DEADLINE = "deadline";
    protected static final String DELIMIT_DEADLINE = "/by";

    protected static final String COMMAND_EVENT = "event";
    protected static final String DELIMIT_EVENT = "/at";

    protected static final String COMMAND_FIND = "find";
    protected static final String DELIMIT_FIND = "";

    protected static final String COMMAND_DUE = "due";
    protected static final String DELIMIT_DUE = "";

    protected static final String COMMAND_LIST = "list";
    protected static final String COMMAND_BYE = "bye";

    /**
     * Returns the raw user input into the Command for execution based on found keywords.
     *
     * @return the Command to be executed
     */
    public Command processInput(String userInput) throws DukeException {
        String[] inputParts = userInput.trim().split(RAW_COMMAND_DELIMIT, SPLIT_INPUT_LIMIT);
        String[] commandParts;
        switch (inputParts[0]) {
        case COMMAND_BYE:
            return new ByeCommand();
        case COMMAND_LIST:
            return new ListCommand();
        case COMMAND_DONE:
            commandParts = splitInputToParts(inputParts, DELIMIT_DONE, COMMAND_DONE);
            return new DoneCommand(commandParts[1]);
        case COMMAND_TODO:
            commandParts = splitInputToParts(inputParts, DELIMIT_TODO, COMMAND_TODO);
            return new AddCommand(commandParts[1]);
        case COMMAND_DEADLINE:
            commandParts = splitInputToParts(inputParts, DELIMIT_DEADLINE, COMMAND_DEADLINE);
            return new AddCommand(commandParts[1], commandParts[2], COMMAND_DEADLINE);
        case COMMAND_EVENT:
            commandParts = splitInputToParts(inputParts, DELIMIT_EVENT, COMMAND_EVENT);
            return new AddCommand(commandParts[1], commandParts[2], COMMAND_EVENT);
        case COMMAND_DELETE:
            commandParts = splitInputToParts(inputParts, DELIMIT_DELETE, COMMAND_DELETE);
            return new DeleteCommand(commandParts[1]);
        case COMMAND_FIND:
            commandParts = splitInputToParts(inputParts, DELIMIT_FIND, COMMAND_FIND);
            return new FindCommand(commandParts[1]);
        case COMMAND_DUE:
            commandParts = splitInputToParts(inputParts, DELIMIT_DUE, COMMAND_DUE);
            return new DueCommand(commandParts[1]);
        default:
            throw new DukeException(Messages.ERROR_MESSAGE_NO_INFO);
        }
    }

    /**
     * Returns the split inputParts into 3 distinct data: Command, Task name, Task Parameters
     *
     * @param inputParts Separated user input into 2 parts by the first spacing
     * @param splitBy The delimiter option to process the 2nd half of the user input
     * @param taskType The identified type of the COMMAND
     * @return processedInputs String array with the input in parts
     */
    public static String[] splitInputToParts(String[] inputParts, String splitBy, String taskType)
            throws DukeException {
        String userInput;
        String taskName;
        String taskParameter = Messages.INIT_STRING;
        try {
            taskName = inputParts[1];
            userInput = taskType;
            //Perform additional splicing if event types are deadline or event
            if (taskType.equals(COMMAND_DEADLINE) || taskType.equals(COMMAND_EVENT)) {
                String[] dateParts = inputParts[1].trim().split(splitBy, SPLIT_INPUT_LIMIT);
                taskName = dateParts[0];
                taskParameter = dateParts[1];
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // Catches out of bounds for two different splicing
            throw new DukeException(Messages.ERROR_MESSAGE_NO_INFO);
        }
        return new String[]{ userInput, taskName, taskParameter };
    }

}
