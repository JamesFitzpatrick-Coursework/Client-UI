package uk.co.thefishlive.maths.tasks;

/**
 * A runner for a specific type of task.
 */
public interface TaskRunner<T extends Task> {

    public void run(T t);

}
