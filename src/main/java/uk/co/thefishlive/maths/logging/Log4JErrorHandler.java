package uk.co.thefishlive.maths.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.Thread.UncaughtExceptionHandler;

public class Log4JErrorHandler implements UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Throwable cause = null;

        do {
            cause = e.getCause();
        } while (cause.getCause() != null);

        Logger logger = LogManager.getLogger(cause.getClass());
        logger.error(cause.getMessage(), e);
    }
}
