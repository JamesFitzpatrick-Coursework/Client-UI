package uk.co.thefishlive.maths.ui;

import com.google.common.base.Throwables;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.resources.Resource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import uk.co.thefishlive.maths.resources.ResourceManager;
import uk.co.thefishlive.maths.resources.exception.ResourceException;

/**
 *
 */
public class UILoader {

    private static final Logger logger = LogManager.getLogger();
    private static Map<String, Image> images = new HashMap<>();

    static {
        ResourceManager resourceManager = Main.getInstance().getResourceManager();

        try {
            images.put("icon-back",         new Image(resourceManager.getResource("images/icons/ic_arrow_back_black_36dp.png").getContent()));
            images.put("icon-close",        new Image(resourceManager.getResource("images/icons/ic_close_black_36dp.png").getContent()));
            images.put("icon-delete",       new Image(resourceManager.getResource("images/icons/ic_delete_grey600_24dp.png").getContent()));
            images.put("icon-group-add",    new Image(resourceManager.getResource("images/icons/ic_group_add_black_36dp.png").getContent()));
            images.put("icon-group",        new Image(resourceManager.getResource("images/icons/ic_group_black_36dp.png").getContent()));
            images.put("icon-home",         new Image(resourceManager.getResource("images/icons/ic_home_black_36dp.png").getContent()));
            images.put("icon-menu",         new Image(resourceManager.getResource("images/icons/ic_menu_white_36dp.png").getContent()));
            images.put("icon-edit",         new Image(resourceManager.getResource("images/icons/ic_mode_edit_grey600_24dp.png").getContent()));
            images.put("icon-user-add",     new Image(resourceManager.getResource("images/icons/ic_person_add_black_36dp.png").getContent()));
            images.put("icon-user",         new Image(resourceManager.getResource("images/icons/ic_person_black_36dp.png").getContent()));
            images.put("icon-settings",     new Image(resourceManager.getResource("images/icons/ic_settings_black_36dp.png").getContent()));
        } catch (ResourceException e) {
            e.printStackTrace();
            Throwables.propagate(e);
        }
    }

    public static UI loadUI(Resource dataFile) throws IOException, ResourceException {
        logger.info("Loading ui {}", dataFile.getPath());
        FXMLLoader loader = new FXMLLoader(dataFile.getUrl());

        Pane pane = (Pane) loader.load();
        replaceIcons(pane.getChildren());

        logger.info("UI Loaded {}", dataFile.getPath());
        return new UI(dataFile.getPath(), pane, (Controller) loader.getController());
    }

    private static void replaceIcons(ObservableList<Node> children) {
        for (Node child : children) {

            for (String string : child.getStyleClass()) {
                if (string.startsWith("icon-")) {
                    if (child instanceof ImageView) {
                        logger.info("Inserting icon {}", string);
                        ((ImageView) child).setImage(images.get(string));
                    }
                }
            }

            if (child instanceof Pane) {
                replaceIcons(((Pane) child).getChildren());
            }
        }
    }
}
