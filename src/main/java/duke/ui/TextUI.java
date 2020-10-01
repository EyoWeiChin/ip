package duke.ui;

import duke.DukeException;
import duke.commands.ResultCommand;
import duke.common.Messages;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Represents the Text User Interface for the application
 */
public class TextUI {

    /**
     * Declare scanner and print stream to handle I/O
     */
    private final Scanner in;
    private final PrintStream out;

    public TextUI() {
        in = new Scanner(System.in);
        out = new PrintStream(System.out);
    }

    /**
     * Prints the welcome message
     */
    public void printWelcomeScreen() {
        out.println(Messages.SINGLE_LINE);
        out.println(Messages.LOGO);
        out.println(Messages.MESSAGE_GREETING);
        out.println();
        out.println(Messages.SINGLE_LINE);
    }

    /**
     * Prints the exit message
     */
    public void printExitMessage() {
        out.println(Messages.SINGLE_LINE);
        out.println(Messages.MESSAGE_GOODBYE);
        out.println(Messages.SINGLE_LINE);
    }

    /**
     *  Prints a single straight line and a newline
     */
    public void printSingleLine() {
        out.println(Messages.SINGLE_LINE);
    }

    /**
     * Prints the error caused
     */
    public void printErrorMessage(DukeException e) {
        out.println(e);
    }

    /**
     * Prints the result of a command
     *
     * @param printResult is a ResultCommand object that has the output of a command.
     */

    public void printResultOfCommand(ResultCommand printResult) {
        //Checks if empty string to prevent printing of duplicate newlines.
        if(!printResult.getResultOfCommand().equals("")) {
            out.println(printResult.getResultOfCommand());
        }
    }

    /**
     * Takes the raw user input from the next line
     *
     * @return the raw user input
     */
    public String getUserInput() {
        return in.nextLine();
    }
}

