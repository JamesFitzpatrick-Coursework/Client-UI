package uk.co.thefishlive.maths.tasks;

/**
 *
 */
public abstract class AbstractTask implements Task {
    private final String name;
    private final Runnable runnable;

    public AbstractTask(String name, Runnable runnable) {
        this.name = name;
        this.runnable = runnable;
    }

    @Override
    public void run() {
        this.runnable.run();
    }

    public String getName() {
        return this.name;
    }
}
