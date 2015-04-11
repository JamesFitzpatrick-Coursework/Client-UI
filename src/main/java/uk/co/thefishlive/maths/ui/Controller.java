package uk.co.thefishlive.maths.ui;

import com.google.common.base.Throwables;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.tasks.TaskManager;
import uk.co.thefishlive.maths.ui.loader.UI;
import uk.co.thefishlive.maths.ui.loader.UILoader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public abstract class Controller implements Initializable, Displayable {

    private static final Logger logger = LogManager.getLogger();

    @FXML protected Pane pnlMenu;

    private UI parent;

    @FXML
    public void btnMenu_Click(MouseEvent event) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), pnlMenu);
        transition.setByX(205);
        transition.play();
    }

    public void setParent(UI parent) {
        this.parent = parent;
    }

    public UI getParent() {
        return parent;
    }

    public void close() {
        Main.getInstance().setCurrentUI(parent);
    }

    @Override
    public void onDisplay() {}

    @Override
    public void onClose() {
        hideLoadingAnimation();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    protected abstract Pane getContentPane();

    protected void showLoadingAnimation() {
        Runnable task = () -> {
            try {
                UI loading = UILoader.loadUI(Main.getInstance().getResourceManager().getResource("ui/loading.fxml"));
                getContentPane().getChildren().add(loading.getPane());
                logger.debug("Loaded loading panel");
            } catch (IOException | ResourceException e) {
                Throwables.propagate(e);
            }
        };

        if (!Thread.currentThread().getName().equals("JavaFX Application Thread")) {
            logger.debug("Loading loading panel from separate thread");
            TaskManager.runTaskSync(task);
        } else {
            logger.debug("Loading loading panel from main thread");
            task.run();
        }
    }

    protected void hideLoadingAnimation() {
        Runnable task = () -> {
            List<Node> children = new ArrayList<>(getContentPane().getChildren());
            children.stream().filter(node -> "pnlLoading".equals(node.getId())).forEach(node -> {
                getContentPane().getChildren().remove(node);
            });
            logger.debug("Removed loading panel");
        };

        if (!Thread.currentThread().getName().equals("JavaFX Application Thread")) {
            logger.debug("Removing loading panel from separate thread");
            TaskManager.runTaskSync(task);
        } else {
            logger.debug("Removing loading panel from main thread");
            task.run();
        }
    }
}
