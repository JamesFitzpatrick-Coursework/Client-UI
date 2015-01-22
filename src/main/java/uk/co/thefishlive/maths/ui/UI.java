package uk.co.thefishlive.maths.ui;

import javafx.scene.layout.Pane;

/**
 *
 */
public class UI {

    private Pane pane;
    private Controller controller;

    UI(Pane pane, Controller controller) {
        this.pane = pane;
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    public Pane getPane() {
        return pane;
    }
}
