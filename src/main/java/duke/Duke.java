package duke;

import duke.parser.Parser;
import duke.storage.SaveManager;
import duke.task.TaskList;
import duke.ui.TextUI;

public class Duke {

    private TextUI ui;
    private SaveManager saveManager;
    private TaskList tasks;
    private Parser mainParser;

    /**
     * Main entry point of the application
     * Greets the User, loads the save file and begins User interaction
     */
    public static void main(String[] args) {
        new Duke("data/duke.txt").run();
    }

    public Duke(String filePath) {
        //Initialize starting resources
        ui = new TextUI();
        SaveManager saveManager = new SaveManager(filePath);
        TaskList tasks = new TaskList();
    }

    public void run() {
        start();
        loopUntilExitCommand();
        exit();
    }

    private void loopUntilExitCommand() {
        boolean stillInteracting = true;

        //Interaction Component
        while (stillInteracting) {
            try {
                //Get User Input and Process it
                String[] processedInputs = mainParser.processInput();
                stillInteracting = mainParser.executeCommand(processedInputs, tasks);
            } catch (DukeException e) {
                ui.printErrorMessage(e);
            } finally {
                ui.printSingleLine();
            }
        }
    }

    private void start() {
        ui.printWelcomeScreen();
        saveManager.loadTaskList(tasks);
    }

    private void exit() {
        ui.printExitMessage();
        System.exit(0);
    }


}
