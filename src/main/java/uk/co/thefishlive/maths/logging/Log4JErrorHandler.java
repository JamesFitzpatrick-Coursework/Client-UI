package uk.co.thefishlive.maths.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uk.co.thefishlive.maths.events.AlertEvent;
import uk.co.thefishlive.maths.events.EventController;

import java.lang.Thread.UncaughtExceptionHandler;

public class Log4JErrorHandler implements UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Throwable cause = e;
        int count = 0;

        // Find the root cause of the exception
        while (cause.getCause() != null && count < 20) {
            cause = cause.getCause();
            count++; // Increment the count so we don't end up with stack issues
        }

        // Get the appropriate logger
        Logger logger = LogManager.getLogger(cause.getClass());
        // Log the exception
        logger.error(cause.getMessage(), e);

        // Alert the user to an exception
        EventController.getInstance().postEvent(new AlertEvent("An error has occurred\n" + cause.getMessage()));
    }
}
