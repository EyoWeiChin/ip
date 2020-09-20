package duke.commands;

/**
 *  Hold the result and information of every command to be printed.
 */
public class ResultCommand {
    private static final String EMPTY_STRING = "";
    private final String resultOfCommand;

    public ResultCommand(String resultOfCommand) {
        this.resultOfCommand = resultOfCommand;
    }

    public ResultCommand() {
        resultOfCommand = EMPTY_STRING;
    }

    public String getResultOfCommand() {
        return resultOfCommand;
    }

}
