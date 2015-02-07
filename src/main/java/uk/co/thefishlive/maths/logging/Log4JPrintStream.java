package uk.co.thefishlive.maths.logging;

import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;

public class Log4JPrintStream extends PrintStream {

    private final Logger logger;
    private final Level level;

    public Log4JPrintStream(PrintStream output, Logger logger, Level level) {
        super(output);
        this.logger = logger;
        this.level = level;
    }

    @Override
    public void print(String string) {
        logger.log(level, string);
    }

    @Override
    public void println(String string) {
        logger.log(level, string);
    }


}
