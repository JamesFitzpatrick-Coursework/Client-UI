package uk.co.thefishlive.maths.ui;

import com.google.common.base.Throwables;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.resources.exception.ResourceException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void onClose() {
        try {
            hideLoadingAnimation();
        } catch (ResourceException | IOException e) {
            Throwables.propagate(e);
        }
    }

    protected abstract Pane getContentPane();

    protected void showLoadingAnimation() throws ResourceException, IOException {
        UI loading = UILoader.loadUI(Main.getInstance().getResourceManager().getResource("ui/loading.fxml"));
        getContentPane().getChildren().add(loading.getPane());
    }

    protected void hideLoadingAnimation() throws ResourceException, IOException {
        List<Node> children = new ArrayList<>(getContentPane().getChildren());
        for (Node node : children) {
            if ("pnlLoading".equals(node.getId())) {
                getContentPane().getChildren().remove(node);
            }
        }
    }
}
