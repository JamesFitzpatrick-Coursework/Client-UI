package uk.co.thefishlive.maths.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import uk.co.thefishlive.maths.resources.Resource;

import java.io.IOException;
import java.net.URL;

/**
 *
 */
public class UILoader {

    public static Pane loadUI(Resource dataFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(dataFile.getUrl());
        return (Pane) loader.load();
    }
}
