package uk.co.thefishlive.maths.tasks.async;

import uk.co.thefishlive.maths.tasks.AbstractTask;

/**
 * A task to be executed by the application asynchronously.
 */
public class AsyncTask extends AbstractTask {
    public AsyncTask(String name, Runnable runnable) {
        super(name, runnable);
    }
}
