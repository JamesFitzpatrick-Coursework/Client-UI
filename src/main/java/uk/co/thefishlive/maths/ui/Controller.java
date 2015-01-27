package uk.co.thefishlive.maths.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.resources.exception.ResourceException;

import java.io.IOException;

public abstract class Controller implements Initializable, Displayable {

    private UI parent;

    public void setParent(UI parent) {
        this.parent = parent;
    }

    public void close() {
        Main.getInstance().setCurrentUI(parent);
    }

    public UI getParent() {
        return parent;
    }

    @Override
    public void onDisplay() {}

    protected void showLoadingAnimation(Pane container) throws ResourceException, IOException {
        UI loading = UILoader.loadUI(Main.getInstance().getResourceManager().getResource("ui/loading.fxml"));
        container.getChildren().add(loading.getPane());
    }
}
