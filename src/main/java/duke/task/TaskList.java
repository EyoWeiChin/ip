package duke.task;

import duke.common.Messages;

import java.util.ArrayList;

/**
 * Represents the entire TaskList. Will store all details of Tasks and methods to support adding and removal
 */
public class TaskList {

    private final ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Takes the new Tasks (Todos, Deadlines, Events) and adds it to the tasks ArrayList
     *
     * @param newTask Takes in as Task object, but in reality it is a subclass.
     */
    public void addTask(Task newTask) {
        tasks.add(newTask);
    }

    public void loadTask(Task newTask) {
        tasks.add(newTask);
    }

    /**
     * Finds and delete the task from the Arraylist
     * @param taskToDeleteInt is the task id to delete
     */
    public void deleteTask(int taskToDeleteInt) {
        tasks.remove(tasks.get(taskToDeleteInt));
    }

    /**
     * Checks the task to complete, and completes it if it is a valid task.
     *
     * @param taskIDToFinish Processed input that identifies the task to complete
     */
    public void completeTask(int taskIDToFinish) {
        tasks.get(taskIDToFinish).setCompleted(true);
    }

    /**
     * Prints all Tasks stored in the tasks ArrayList
     */
    public void listAllTasks() {
        if (tasks.size() == 0) {
            System.out.println(Messages.MESSAGE_NO_TASKS_DUE);
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ": " + tasks.get(i));
            }
        }
    }
}
