package uk.co.thefishlive.maths.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.dialog.Dialogs;

import uk.co.thefishlive.maths.events.AlertEvent;
import uk.co.thefishlive.maths.events.EventController;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Random;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

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
        showDialog(cause.getMessage(), e);
    }

    public void showDialog(String message, Throwable ex) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Exception");
        alert.setHeaderText(getExceptionMessage());
        alert.setContentText(message);

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(false);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    private String[] messages = new String[] {
        "Oops, we seem to have hit an exception...",
        "Ohh noes, something has gone wrong.",
        "Dammit, what went wrong this time.",
        "This seems to happen alot.",
        "Errors are a portal to discovery, or just a pain.",
        "Nothing to see here, just a working program."
    };
    private Random random = new Random();

    public String getExceptionMessage() {
        return messages[random.nextInt(messages.length)];
    }
}
