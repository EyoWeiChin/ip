package duke;

import duke.commands.ByeCommand;
import duke.commands.Command;
import duke.parser.Parser;
import duke.storage.SaveManager;
import duke.task.TaskList;
import duke.ui.TextUI;

/**
 * Entry point for Duke.
 * Greets the User, loads the save file and begins User interaction
 */
public class Duke {

    protected static final String FIXED_FILE_PATH = "data/duke.txt";
    private final TextUI ui;
    private final SaveManager saveManager;
    private final Parser parser;
    private TaskList tasks;

    public static void main(String[] args) {
        new Duke(FIXED_FILE_PATH).run();
    }

    /**
     * Initialize the starting resources that is required by the application
     *
     * @param filePath is the path of the save file that will be loaded at the start
     */
    public Duke(String filePath) {
        this.ui = new TextUI();
        this.parser = new Parser();
        this.saveManager = new SaveManager(filePath);
        try {
            this.tasks = saveManager.loadTaskList();
        } catch (DukeException fileReadError) {
            ui.printErrorMessage(fileReadError);
            this.tasks = new TaskList();
        }
    }

    /**
     * Runs the program until the Bye Command
     */
    public void run() {
        start();
        loopUntilExitCommand();
        exit();
    }

    /**
     * The main looping logic that processes the user inputs and parses the command.
     * Once the input has been processed, it will execute the command and prints the Command result
     */
    private void loopUntilExitCommand() {
        boolean stillInteracting = true;
        while(stillInteracting) {
            try {
                Command command = parser.processInput(ui.getUserInput());
                ui.printResultOfCommand(command.execute(tasks, saveManager));
                stillInteracting = !(command instanceof ByeCommand);
            } catch (DukeException error) {
                ui.printErrorMessage(error);
            } finally {
                ui.printSingleLine();
            }
        }
    }

    private void start() {
        ui.printWelcomeScreen();
    }

    private void exit() {
        ui.printExitMessage();
        System.exit(0);
    }


}
