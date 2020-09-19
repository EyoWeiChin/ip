package duke.commands;

public class ResultCommand extends Command {
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
