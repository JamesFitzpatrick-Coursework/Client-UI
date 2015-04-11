package uk.co.thefishlive.maths.tasks.sync;

import uk.co.thefishlive.maths.tasks.TaskRunner;

import javafx.application.Platform;

public class SyncTaskRunner implements TaskRunner<SyncTask> {
    @Override
    public void run(final SyncTask task) {
        Platform.runLater(task::run);
    }
}
