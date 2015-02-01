package uk.co.thefishlive.maths.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.Thread.UncaughtExceptionHandler;

public class Log4JErrorHandler implements UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Throwable cause = null;
        int count = 0;

        do {
            cause = e.getCause();
            count++;
        } while (cause.getCause() != null && count < 5);

        Logger logger = LogManager.getLogger(cause.getClass());
        logger.error(cause.getMessage(), e);
    }
}
