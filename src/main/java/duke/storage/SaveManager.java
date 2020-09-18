package duke.storage;

import duke.common.Messages;
import duke.task.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static duke.task.TaskList.listAllTasks;

public class SaveManager {
    //Constant Variables for Save / Load logic
    protected static final String DELIMIT_SAVE_FILE = " | ";
    protected static final String DELIMIT_SAVE_FILE_REGEX = "\\|";
    protected static final int SPLIT_SAVE_LIMIT = 5;
    protected static final String SAVE_TODO = "T";
    protected static final String SAVE_DEADLINE = "D";
    protected static final String SAVE_EVENT = "E";
    protected static final String SAVE_COMPLETED_TASK = "1";
    protected static final String SAVE_NOT_COMPLETED_TASK = "0";

    /**
     * Declare file paths for saving and loading
     */
    //protected static final File filePath = new File("data/duke.txt");
    protected static final File DATA_FOLDER = new File("data");

    private static File filePath;

    //TODO: Split the filePath in the constructor so that it can use a dynamic DATA_FOLDER
    public SaveManager(String filePath) {
        this.filePath = new File(filePath);
    }

    /**
     * Checks if save folder and save file exists, if not create them.
     * If they exist, call savedFileReader() to read it.
     */
    public static void loadTaskList(TaskList tasks) {
        if (!DATA_FOLDER.exists()) {
            DATA_FOLDER.mkdir();
            System.out.println(Messages.MESSAGE_CREATED_FOLDER);
        }
        try {
            savedFileReader(tasks);
        } catch (java.io.FileNotFoundException notFoundExcept) {
            try {
                //Create the save file if exception was thrown
                filePath.createNewFile();
                System.out.println(Messages.MESSAGE_CREATED_SAVE_FILE);
                System.out.println(Messages.SINGLE_LINE);
            } catch (java.io.IOException existExcept) {
                System.out.println(Messages.ERROR_DUPLICATE_SAVE + existExcept);
            }
        }
        //Print the added task if any
        if (tasks.getTasks().size() > 0) {
            System.out.println(Messages.MESSAGE_SUCCESSFUL_LOAD);
            listAllTasks();
        }
    }

    /**
     * Reads the saved file and adds the saved data to the data structure.
     *
     * @throws java.io.FileNotFoundException if SAVE_FILE does not exist
     */
    private static void savedFileReader(TaskList tasks) throws java.io.FileNotFoundException {
        Scanner fileScanner = new Scanner(filePath);
        while(fileScanner.hasNext()) {
            String currentLine = fileScanner.nextLine();
            String[] stringParts = currentLine.split(DELIMIT_SAVE_FILE_REGEX, SPLIT_SAVE_LIMIT);
            switch (stringParts[1].trim()) {
            case SAVE_TODO:
                Task loadTodo = new Todo(stringParts[2].trim());
                if (stringParts[0].trim().equals(SAVE_COMPLETED_TASK)) {
                    loadTodo.setCompleted(true);
                }
                tasks.loadTask(loadTodo);
                break;
            case SAVE_DEADLINE:
                Task loadDeadline = new Deadline(stringParts[2].trim(), stringParts[3].trim());
                if (stringParts[0].trim().equals(SAVE_COMPLETED_TASK)) {
                    loadDeadline.setCompleted(true);
                }
                tasks.loadTask(loadDeadline);
                break;
            case SAVE_EVENT:
                Task loadEvent = new Event(stringParts[2].trim(), stringParts[3].trim());
                if (stringParts[0].trim().equals(SAVE_COMPLETED_TASK)) {
                    loadEvent.setCompleted(true);
                }
                tasks.loadTask(loadEvent);
                break;
            default:
                break;
            }
        }
    }

    public static void saveTaskList(TaskList tasks) {
        StringBuilder saveString = new StringBuilder(Messages.INIT_STRING);
        //Loop through the Task ArrayList and build the string to save
        for (Task saveTask: tasks.getTasks()) {
            if (saveTask.isCompleted()) {
                saveString.append(SAVE_COMPLETED_TASK + DELIMIT_SAVE_FILE);
            } else {
                saveString.append(SAVE_NOT_COMPLETED_TASK + DELIMIT_SAVE_FILE);
            }
            if (saveTask instanceof Todo) {
                saveString.append(SAVE_TODO + DELIMIT_SAVE_FILE);
                saveString.append(saveTask.getTaskName()).append(DELIMIT_SAVE_FILE);
            } else if (saveTask instanceof Deadline) {
                saveString.append(SAVE_DEADLINE + DELIMIT_SAVE_FILE);
                saveString.append(saveTask.getTaskName()).append(DELIMIT_SAVE_FILE);
                saveString.append(((Deadline) saveTask).getDueTime()).append(DELIMIT_SAVE_FILE);
            } else if (saveTask instanceof Event) {
                saveString.append(SAVE_EVENT + DELIMIT_SAVE_FILE);
                saveString.append(saveTask.getTaskName()).append(DELIMIT_SAVE_FILE);
                saveString.append(((Event) saveTask).getDuration()).append(DELIMIT_SAVE_FILE);
            }
            saveString.append(System.lineSeparator());
        }
        try {
            FileWriter fw = new FileWriter(filePath);
            fw.write(saveString.toString());
            fw.close();
        } catch (IOException e) {
            System.out.println(Messages.ERROR_CANNOT_WRITE + e.getMessage());
        }
    }
}
