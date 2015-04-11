package uk.co.thefishlive.maths.tasks;

/**
 * Represents a task to be executed by the application.
 */
public interface Task extends Runnable {
    /**
     * Get the name of this task.
     *
     * @return the name of this task
     */
    public String getName();
}
