package uk.co.thefishlive.maths.tasks;

import com.google.common.collect.Maps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uk.co.thefishlive.maths.tasks.async.AsyncTask;
import uk.co.thefishlive.maths.tasks.async.AsyncTaskRunner;
import uk.co.thefishlive.maths.tasks.sync.SyncTask;
import uk.co.thefishlive.maths.tasks.sync.SyncTaskRunner;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
@SuppressWarnings("unchecked")
public class TaskManager {

    private static final Logger logger = LogManager.getLogger();
    private static Map<Class<? extends Task>, TaskRunner> runners = Maps.newHashMap();
    private static AtomicInteger taskId = new AtomicInteger(0);

    static {
        runners.put(AsyncTask.class, new AsyncTaskRunner());
        runners.put(SyncTask.class, new SyncTaskRunner());
    }

    protected static <T extends Task> TaskRunner<T> getRunner(Class<T> clazz) {
        if (!runners.containsKey(clazz)) {
            throw new IllegalArgumentException("Cannot find runner for Task type " + clazz.getName());
        }

        return runners.get(clazz);
    }

    protected static void runTask(Task task) {
        TaskRunner runner = getRunner(task.getClass());
        logger.info("Running task " + task.getName() + " of type " + task.getClass().getSimpleName());
        runner.run(task);
    }

    /**
     * Run a task asynchronously.
     *
     * @param name the name of the task to be run
     * @param runnable the task to be run
     */
    public static void runTaskAsync(String name, Runnable runnable) {
        runTask(new AsyncTask(name, runnable));
    }

    /**
     * Run a task asynchronously.
     *
     * @param runnable the task to be run
     */
    public static void runTaskAsync(Runnable runnable) {
        runTaskAsync("AsyncTask-" + taskId.getAndIncrement(), runnable);
    }

    /**
     * Run a task on the main JavaFX thread.
     *
     * @param name the name of the task to be run
     * @param runnable the task to be run
     */
    public static void runTaskSync(String name, Runnable runnable) {
        runTask(new SyncTask(name, runnable));
    }

    /**
     * Run a task on the main JavaFX thread.
     *
     * @param runnable the task to be run
     */
    public static void runTaskSync(Runnable runnable) {
        runTaskSync("SyncTask-" + taskId.getAndIncrement(), runnable);
    }

}
