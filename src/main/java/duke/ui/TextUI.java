package duke.ui;

import duke.DukeException;
import duke.common.Messages;

public class TextUI {

    /**
     * Prints the welcome message
     */
    public static void printWelcomeScreen() {
        System.out.println(Messages.SINGLE_LINE);
        System.out.println(Messages.LOGO);
        System.out.println(Messages.MESSAGE_GREETING);
        System.out.println();
        System.out.println(Messages.SINGLE_LINE);
    }

    /**
     * Prints the exit message
     */
    public static void printExitMessage() {
        System.out.println(Messages.SINGLE_LINE);
        System.out.println(Messages.MESSAGE_GOODBYE);
        System.out.println(Messages.SINGLE_LINE);
    }

    /**
     *  Prints a single straight line and a newline
     */
    public static void printSingleLine() {
        System.out.println(Messages.SINGLE_LINE);
    }

    /**
     * Prints the error caused
     */
    public static void printErrorMessage(DukeException e) {
        System.out.println(e);
    }

}

