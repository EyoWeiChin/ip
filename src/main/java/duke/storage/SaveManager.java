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

/**
 * Represents a save file with methods to save / load / create the save file
 */
public class SaveManager {
    /**
     * Constant variables used for save and loading command logic
     */
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
    protected static final File DATA_FOLDER = new File("data");

    private final File filePath;

    //TODO: Split the filePath in the constructor so that it can use a dynamic DATA_FOLDER
    public SaveManager(String filePath) {
        this.filePath = new File(filePath);
    }


    /**
     * Handles creating a TaskList and loading the result. Will call methods to create file if needed
     * Prints the result of loading at the end
     *
     * @return tasks the TaskList object that has the loaded tasks
     * @throws DukeException if duplicate file is found
     */
    public TaskList loadTaskList() throws DukeException {
        TaskList loadedTasks = new TaskList();
        try {
            createDirIfNeeded();
            createFileIfNeeded();
        } catch (IOException fileExists) {
            //Do nothing as the files would be created when needed
            //This will only catch rare exceptions of duplicate files created after the first check
        }

        savedFileReader(loadedTasks);
        printLoadResult(loadedTasks);
        return loadedTasks;
    }

    /**
     * Checks if any tasks were loaded and print them
     *
     * @param loadedTasks is the TaskList object to load the saved data into
     */
    private void printLoadResult(TaskList loadedTasks) {
        if (loadedTasks.getTasks().size() > 0) {
            System.out.println(Messages.MESSAGE_SUCCESSFUL_LOAD);
            loadedTasks.listAllTasks();
        } else {
            System.out.println(Messages.MESSAGE_NO_TASK_LOADED);
        }
    }

    private void createDirIfNeeded() throws IOException, DukeException {
        if (!DATA_FOLDER.exists()) {
            Files.createDirectories(Paths.get(String.valueOf(DATA_FOLDER)));
            throw new DukeException(Messages.MESSAGE_CREATED_SAVE_FOLDER);
        }
    }

    private void createFileIfNeeded() throws IOException, DukeException {
        if (!filePath.exists()) {
            Files.createFile(Paths.get(String.valueOf(filePath)));
            throw new DukeException(Messages.MESSAGE_CREATED_SAVE_FILE);
        }
    }

    /**
     * Reads the saved file and adds the saved data to the data structure
     * .
     * @param tasks is the TaskList object to load the saved data into
     * @throws DukeException if save file cannot be found
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
     *
     * @param tasks is the TaskList object to load the saved data into
     */
    public void saveTaskList(TaskList tasks) {
        StringBuilder saveString = new StringBuilder(Messages.INIT_STRING);
        //Here it will loop through the TaskList and build the string to be saved.
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
                saveString.append(saveTask.getDueTime()).append(DELIMIT_SAVE_FILE);
            } else if (saveTask instanceof Event) {
                saveString.append(SAVE_EVENT + DELIMIT_SAVE_FILE);
                saveString.append(saveTask.getTaskName()).append(DELIMIT_SAVE_FILE);
                saveString.append(saveTask.getDueTime()).append(DELIMIT_SAVE_FILE);
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
