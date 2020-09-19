package duke;

import duke.commands.ByeCommand;
import duke.commands.Command;
import duke.parser.Parser;
import duke.storage.SaveManager;
import duke.task.TaskList;
import duke.ui.TextUI;

public class Duke {

    private final TextUI ui;
    private final SaveManager saveManager;
    private final Parser parser;
    private TaskList tasks;

    /**
     * Main entry point of the application
     * Greets the User, loads the save file and begins User interaction
     */
    public static void main(String[] args) {
        new Duke("data/duke.txt").run();
    }

    public Duke(String filePath) {
        //Initialize starting resources
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

    public void run() {
        start();
        loopUntilExitCommand();
        exit();
    }

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
