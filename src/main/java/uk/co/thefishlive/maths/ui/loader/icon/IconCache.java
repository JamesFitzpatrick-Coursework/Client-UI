package uk.co.thefishlive.maths.ui.loader.icon;

import com.google.common.collect.Maps;
import javafx.scene.image.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.resources.handlers.ImageResource;
import uk.co.thefishlive.maths.ui.loader.css.CssElementList;

import java.util.Map;

/**
 *
 */
public class IconCache {

    private static final Logger logger = LogManager.getLogger();

    private Map<String, String> paths = Maps.newHashMap();
    private Map<IconData, Icon> icons = Maps.newHashMap();

    public IconCache() {
        registerIconName("back",       "arrow_back");
        registerIconName("close",      "close");
        registerIconName("delete",     "delete");
        registerIconName("group-add",  "group_add");
        registerIconName("group",      "group");
        registerIconName("home",       "home");
        registerIconName("menu",       "menu");
        registerIconName("edit",       "mode_edit");
        registerIconName("user-add",   "person_add");
        registerIconName("user",       "person");
        registerIconName("settings",   "settings");
    }

    public Icon getIcon(CssElementList css) throws ResourceException {
        if (css.get("icon").getValue() == null) {
            return null;
        }

        IconData data = new IconData(css);
        return getIcon(data);
    }

    public Icon getIcon(IconData data) throws ResourceException {
        Icon icon = icons.get(data);

        if (icon == null) {
            icon = loadIcon(data);
            icons.put(data, icon);
        } else {
            logger.info("Found cached icon for " + data.toString());
        }

        return icon;
    }

    private Icon loadIcon(IconData data) throws ResourceException {
        String path = paths.get(data.getId());

        if (path == null) {
            throw new ResourceException("Cannot find icon for name " + data.getId());
        }

        path += "_" + data.getColor() + "_" + data.getSize() + ".png";

        logger.info("Loading icon " + path);

        ImageResource resource = Main.getInstance().getResourceManager().getResourceAs(path, ImageResource.class);
        return new Icon(data, resource.getImage());
    }

    private void registerIconName(String name, String filename) {
        this.paths.put(name, "images/icons/ic_" + filename);
    }
}
