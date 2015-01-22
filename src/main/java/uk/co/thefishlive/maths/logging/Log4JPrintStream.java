package uk.co.thefishlive.maths.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;

public class Log4JPrintStream extends PrintStream {

    private final Logger logger;

    public Log4JPrintStream(PrintStream output, Logger logger) {
        super(output);
        this.logger = logger;
    }

    @Override
    public void print(String string) {
        logger.log(Level.INFO, string);
    }

}
