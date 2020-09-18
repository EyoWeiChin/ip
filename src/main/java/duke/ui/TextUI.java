package duke.ui;

import duke.DukeException;
import duke.common.Messages;

import java.io.PrintStream;
import java.util.Scanner;

public class TextUI {

    /**
     * Declare scanner and print stream to handle I/O
     */
    private static Scanner in;
    private static PrintStream out;

    public TextUI() {
        this.in = new Scanner(System.in);
        this.out = new PrintStream(System.out);
    }

    /**
     * Prints the parameter and a new line
     * @param printThis
     */
    public static void printString(String printThis) {
        out.println(printThis);
    }

    /**
     * Prints the welcome message
     */
    public static void printWelcomeScreen() {
        out.println(Messages.SINGLE_LINE);
        out.println(Messages.LOGO);
        out.println(Messages.MESSAGE_GREETING);
        out.println();
        out.println(Messages.SINGLE_LINE);
    }

    /**
     * Prints the exit message
     */
    public static void printExitMessage() {
        out.println(Messages.SINGLE_LINE);
        out.println(Messages.MESSAGE_GOODBYE);
        out.println(Messages.SINGLE_LINE);
    }

    /**
     *  Prints a single straight line and a newline
     */
    public static void printSingleLine() {
        out.println(Messages.SINGLE_LINE);
    }

    /**
     * Prints the error caused
     */
    public static void printErrorMessage(DukeException e) {
        out.println(e);
    }

    /**
     * Takes the raw user input from the next line
     *
     * @return the raw user input
     */
    public static String getUserInput() {
        return in.nextLine();
    }
}

