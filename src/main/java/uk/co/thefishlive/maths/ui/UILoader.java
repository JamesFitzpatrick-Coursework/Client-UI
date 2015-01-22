package uk.co.thefishlive.maths.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import uk.co.thefishlive.maths.resources.Resource;

import java.io.IOException;
import uk.co.thefishlive.maths.resources.exception.ResourceException;

/**
 *
 */
public class UILoader {

    public static UI loadUI(Resource dataFile) throws IOException, ResourceException {
        FXMLLoader loader = new FXMLLoader(dataFile.getUrl());
        return new UI((Pane) loader.load(), (Controller) loader.getController());
    }
}
