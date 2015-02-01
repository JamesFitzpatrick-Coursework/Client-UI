package uk.co.thefishlive.maths.ui.loader;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import uk.co.thefishlive.maths.ui.Controller;

/**
 *
 */
public class UI {

    private String name;
    private Pane pane;
    private Controller controller;
    private Scene scene;

    UI(String name, Pane pane, Controller controller) {
        this.name = name;
        this.pane = pane;
        this.controller = controller;
    }

    public String getName() {
        return this.name;
    }

    public Controller getController() {
        return controller;
    }

    public Pane getPane() {
        return pane;
    }

    public void onDisplay() {
        this.controller.onDisplay();
    }

    public void onClose() {
        this.controller.onClose();
    }

    public void setParent(UI ui) {
        this.controller.setParent(ui);
    }

    public Scene buildScene() {
        if (this.scene == null) {
            this.scene = new Scene(this.pane);
        }

        return this.scene;
    }

    public <T extends Controller> T getController(Class<T> controller) {
        return (T) this.controller;
    }
}
