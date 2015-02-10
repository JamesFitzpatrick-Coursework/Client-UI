package uk.co.thefishlive.maths.ui.loader.icon;

import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.resources.handlers.ImageResource;
import uk.co.thefishlive.maths.ui.loader.css.CssElementList;

import java.util.Map;
import java.util.ResourceBundle;

/**
 *
 */
public class IconCache {

    private static final Logger logger = LogManager.getLogger();

    private final ResourceBundle iconData;

    private Map<IconData, Icon> icons = Maps.newHashMap();

    public IconCache() {
        this.iconData = ResourceBundle.getBundle("properties.icons");
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
        if (!iconData.containsKey(data.getInternalName())) {
            throw new ResourceException("Cannot find icon for name " + data.getId());
        }

        String path = findIconPath(data);

        logger.info("Loading icon " + path);

        ImageResource resource = Main.getInstance().getResourceManager().getResourceAs(path, ImageResource.class);
        return new Icon(data, resource.getImage());
    }

    private String findIconPath(IconData data) {
        return "images/icons/ic_" + iconData.getString(data.getInternalName()) + "_" + data.getColor() + "_" + data.getSize() + ".png";
    }
}
