import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        final String LOGO = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        final String SINGLE_LINE =
                "____________________________________________________________";

        //Duke's Greeting
        System.out.println(SINGLE_LINE);
        System.out.println(LOGO);
        System.out.println("Hello! I'm Duke!");
        System.out.println("What can I do for you?");
        System.out.println(SINGLE_LINE);

        //Echoing
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        while(!userInput.equals("bye")) {
            userInput = scanner.nextLine();
            System.out.println(SINGLE_LINE);
            System.out.println(userInput);
            System.out.println(SINGLE_LINE);
        }

        //Exiting
        System.out.println("Bye! Hope to see you again soon!");
        System.out.println(SINGLE_LINE);
    }
}
