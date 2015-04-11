package uk.co.thefishlive.maths.tasks.sync;

import uk.co.thefishlive.maths.tasks.AbstractTask;

/**
 * A task to be executed by the application on the main JavaFx thread.
 */
public class SyncTask extends AbstractTask {

    public SyncTask(String name, Runnable runnable) {
        super(name, runnable);
    }
}
