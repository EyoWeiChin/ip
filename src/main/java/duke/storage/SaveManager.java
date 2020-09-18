package duke.storage;

import duke.DukeException;
import duke.common.Messages;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


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

    private File filePath;

    //TODO: Split the filePath in the constructor so that it can use a dynamic DATA_FOLDER
    public SaveManager(String filePath) {
        this.filePath = new File(filePath);
    }

    /**
     * Checks if save folder and save file exists, if not create them.
     * If they exist, call savedFileReader() to read it.
     */
    public TaskList loadTaskList() throws DukeException {
        TaskList loadedTasks = new TaskList();
        try {
            Files.createDirectories(Paths.get(String.valueOf(DATA_FOLDER)));
            Files.createFile(Paths.get(String.valueOf(filePath)));
            throw new DukeException(Messages.MESSAGE_CREATED_SAVE_FILE);
        } catch (IOException fileExists) {
            //The actual reading takes place here
            savedFileReader(loadedTasks);
        }

        if (loadedTasks.getTasks().size() > 0) {
            System.out.println(Messages.MESSAGE_SUCCESSFUL_LOAD);
            loadedTasks.listAllTasks();
        }
        return loadedTasks;
    }

    /**
     * Reads the saved file and adds the saved data to the data structure.
     * @param tasks
     * @throws DukeException
     */
    private void savedFileReader(TaskList tasks) throws DukeException {
        Scanner fileScanner;
        try {
            fileScanner = new Scanner(filePath);
        } catch (java.io.FileNotFoundException existExcept) {
            throw new DukeException(Messages.ERROR_DUPLICATE_SAVE + existExcept);
        }
        while(fileScanner.hasNext()) {
            String[] stringParts = fileScanner.nextLine().split(DELIMIT_SAVE_FILE_REGEX, SPLIT_SAVE_LIMIT);
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

    /**
     * Saves the current TaskList to a save file in the correct format that can be loaded.
     * @param tasks
     */
    public void saveTaskList(TaskList tasks) {
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
