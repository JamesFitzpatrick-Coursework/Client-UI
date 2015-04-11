package uk.co.thefishlive.maths.tasks.async;

import uk.co.thefishlive.maths.tasks.TaskRunner;

public class AsyncTaskRunner implements TaskRunner<AsyncTask> {
    @Override
    public void run(final AsyncTask task) {
        Thread thread = new Thread(task::run);
        thread.setName(task.getName());
        thread.run();
    }
}
