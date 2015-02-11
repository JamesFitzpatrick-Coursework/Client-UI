package uk.co.thefishlive.maths.ui.loader;

import com.google.common.base.Throwables;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.resources.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import uk.co.thefishlive.maths.resources.ResourceManager;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.maths.ui.loader.css.CssElementList;
import uk.co.thefishlive.maths.ui.loader.css.CssException;
import uk.co.thefishlive.maths.ui.loader.css.CssParser;
import uk.co.thefishlive.maths.ui.loader.icon.Icon;
import uk.co.thefishlive.maths.ui.loader.icon.IconCache;

/**
 *
 */
public class UILoader {

    private static final Logger logger = LogManager.getLogger();
    private static final CssParser parser = new CssParser();

    private static IconCache iconCache = null;
    private static List<Resource> stylesheets = new ArrayList<>();

    static {
        try {
            iconCache = new IconCache();
        } catch (ResourceException e) {
            Throwables.propagate(e);
        }
    }

    public static UI loadUI(String dataFile) throws IOException, ResourceException {
        return loadUI(Main.getInstance().getResourceManager().getResource(dataFile));
    }

    public static UI loadUI(Resource dataFile) throws IOException, ResourceException {
        logger.info("Loading ui {}", dataFile.getPath());
        FXMLLoader loader = new FXMLLoader(dataFile.getUrl(), ResourceBundle.getBundle("lang.strings"));

        if (loader.getController() != null && !(loader.getController() instanceof Controller)) {
            throw new ResourceException(String.format("Controller specified for ui %s is not valid controller (%s)", dataFile.getPath(), loader.getController().getClass().getName()));
        }

        Pane pane = loader.load();

        for (Resource resource : stylesheets) {
            logger.debug("Adding style sheet {}", resource.getUrl().toExternalForm());
            pane.getStylesheets().add(resource.getUrl().toExternalForm());
        }

        replaceIcons(pane.getChildren());

        logger.info("UI Loaded {}", dataFile.getPath());
        return new UI(dataFile.getPath(), pane, loader.getController());
    }
    
    public static void registerStyleSheet(Resource resource) {
        stylesheets.add(resource);
    }

    public static IconCache getIconCache() {
        return iconCache;
    }

    private static void replaceIcons(ObservableList<Node> children) throws ResourceException {
        for (Node child : children) {
            if (child instanceof ImageView) {
                try {
                    CssElementList css = parser.parseStyleString(child.getStyle());
                    Icon icon = iconCache.getIcon(css);

                    if (icon != null) {
                        ((ImageView) child).setImage(icon.getImage());
                    }
                } catch (CssException e) {
                    throw new ResourceException(e);
                }
            }

            if (child instanceof Pane) {
                replaceIcons(((Pane) child).getChildren());
            }
        }
    }
}
