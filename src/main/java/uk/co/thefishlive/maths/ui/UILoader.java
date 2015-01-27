package uk.co.thefishlive.maths.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import uk.co.thefishlive.maths.resources.Resource;

import java.io.IOException;

import uk.co.thefishlive.maths.resources.exception.ResourceException;

/**
 *
 */
public class UILoader {

    private static final Logger logger = LogManager.getLogger();

    public static UI loadUI(Resource dataFile) throws IOException, ResourceException {
        logger.info("Loading ui {}", dataFile.getPath());
        FXMLLoader loader = new FXMLLoader(dataFile.getUrl());
        logger.info("UI Loaded {}", dataFile.getPath());
        return new UI(dataFile.getPath(), (Pane) loader.load(), (Controller) loader.getController());
    }
}
