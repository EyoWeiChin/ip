package duke.task;

import duke.common.Messages;

import java.util.ArrayList;

public class TaskList {

    private static ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public static ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Takes the new Tasks (Todos, Deadlines, Events) and adds it to the tasks ArrayList
     *
     * @param newTask Takes in as Task object, but in reality it is a subclass.
     */
    public static void addTask(Task newTask) {
        tasks.add(newTask);
        System.out.println(Messages.SINGLE_LINE);
        System.out.println("+ " + newTask);
        System.out.println(Messages.MESSAGE_TASKS_LEFT + Task.getTotalTasks());
    }

    public static void loadTask(Task newTask) {
        tasks.add(newTask);
    }

    /**
     * Finds and delete the task from the Arraylist
     * @param taskToDelete is the task id to delete
     */
    public static void deleteTask(String taskToDelete) {
        int taskToDeleteInt = Integer.parseInt(taskToDelete) - 1;
        if (taskToDeleteInt < 0 || taskToDeleteInt >= tasks.size()) {
            System.out.println(Messages.MESSAGE_NO_SUCH_TASK);
        } else {
            System.out.println(Messages.MESSAGE_REMOVED_TASK);
            System.out.println(tasks.get(taskToDeleteInt));
            System.out.println(Messages.SINGLE_LINE);
            tasks.remove(tasks.get(taskToDeleteInt));
        }
    }

    /**
     * Checks the task to complete, and completes it if it is a valid task.
     *
     * @param taskIDInString Processed input that identifies the task to complete
     */
    public static void completeTask(String taskIDInString) {
        // Change from String to Integer then 0-base to 1-base indexing by deducting 1
        int taskIDToFinish = Integer.parseInt(taskIDInString) - 1;
        if (taskIDToFinish < 0 || taskIDToFinish >= tasks.size()) {
            System.out.println(Messages.MESSAGE_NO_SUCH_TASK);
        } else if (Task.getTotalTasks() == 0) {
            System.out.println(Messages.MESSAGE_NO_REMAINING_TASKS);
        } else if (tasks.get(taskIDToFinish).isCompleted()) {
            System.out.println(Messages.MESSAGE_TASK_ALREADY_COMPLETED);
        } else {
            //Set the task to be completed and check remaining tasks.
            tasks.get(taskIDToFinish).setCompleted(true);
            System.out.println("'" + tasks.get(taskIDToFinish).getTaskName().trim() + "'");
            System.out.println(Messages.MESSAGE_TASK_COMPLETED);
            if(Task.getTotalTasks() == 0) {
                System.out.println(Messages.MESSAGE_ALL_TASK_COMPLETED);
            } else {
                System.out.println(Messages.MESSAGE_TASKS_LEFT + Task.getTotalTasks());
            }
        }
    }

    /**
     * Prints all Tasks stored in the tasks ArrayList
     */
    public static void listAllTasks() {
        if (tasks.size() == 0) {
            System.out.println(Messages.MESSAGE_NO_TASKS_DUE);
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ": " + tasks.get(i));
            }
        }
    }
}
