import java.util.ArrayList;
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

        //Greet
        System.out.println(SINGLE_LINE);
        System.out.println(LOGO);
        System.out.println("Hello! I'm Duke!");
        System.out.println("What can I do for you?");
        System.out.println(SINGLE_LINE);

        //Interaction
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        boolean stillInteracting = true;
        ArrayList<String> tasks = new ArrayList<String>();

        while (stillInteracting) {
            userInput = scanner.nextLine();
            switch (userInput) {
            case "bye":
                stillInteracting = false;
                break;
            case "list":
                if (tasks.size() == 0) {
                    System.out.println("Fortunately, you have no tasks due");
                } else {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(i + 1 + ": " + tasks.get(i));
                    }
                }
                System.out.println(SINGLE_LINE);
                break;
            default:
                System.out.println(SINGLE_LINE);
                System.out.println("+ " + userInput);
                System.out.println(SINGLE_LINE);
                tasks.add(userInput);
                break;
            }
        }

        //Exit
        System.out.println(SINGLE_LINE);
        System.out.println("Bye! Hope to see you again soon!");
        System.out.println(SINGLE_LINE);
    }
}
