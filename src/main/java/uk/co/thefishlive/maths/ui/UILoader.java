package uk.co.thefishlive.maths.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

/**
 *
 */
public class UILoader {

    public static Pane loadUI(URL dataFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(dataFile);
        return (Pane) loader.load();
    }
}
