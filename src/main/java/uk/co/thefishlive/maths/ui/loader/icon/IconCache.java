package uk.co.thefishlive.maths.ui.loader.icon;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.resources.ResourceReader;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.resources.handlers.ImageResource;
import uk.co.thefishlive.maths.resources.handlers.JsonResource;
import uk.co.thefishlive.maths.ui.loader.css.CssElementList;

import java.util.Map;
import java.util.ResourceBundle;

/**
 *
 */
public class IconCache {

    private static final Logger logger = LogManager.getLogger();

    private Map<IconData, Icon> icons = Maps.newHashMap();
    private Map<IconData, String> iconData = Maps.newHashMap();

    public IconCache() throws ResourceException {
        // Load the icon data from the data file
        JsonObject json = Main.getInstance().getResourceManager().getResourceAs("data/icons.dat", JsonResource.class).parse();

        // Load these icons into memory
        for (JsonElement element : json.getAsJsonArray("icons")) {
            JsonObject icon = element.getAsJsonObject();
            JsonObject iconData = icon.getAsJsonObject("icon");

            // Put the new icon into memory
            this.iconData.put(
                    new IconData(
                            iconData.get("name").getAsString(),
                            iconData.get("colour").getAsString(),
                            iconData.get("size").getAsString()
                    ),
                    "images/icons/" + icon.get("path").getAsString()
            );
        }
    }

    public Icon getIcon(CssElementList css) throws ResourceException {
        // Check the css is a valid icon
        if (css.get("icon").getValue() == null) {
            return null;
        }

        // Load the icon
        IconData data = new IconData(css);
        return getIcon(data);
    }

    public Icon getIcon(IconData data) throws ResourceException {
        Icon icon = icons.get(data);

        // If the icon is not already cached load and cache it
        if (icon == null) {
            icon = loadIcon(data);
            icons.put(data, icon);
        } else {
            logger.info("Found cached icon for " + data.toString());
        }

        return icon;
    }

    private Icon loadIcon(IconData data) throws ResourceException {
        String path = iconData.get(data);

        // Icon not found
        if (path == null) {
            throw new ResourceException("Cannot find icon for data " + data.toString());
        }

        logger.info("Loading icon " + path);

        // Return the Icon
        ImageResource resource = Main.getInstance().getResourceManager().getResourceAs(path, ImageResource.class);
        return new Icon(data, resource.getImage());
    }
}
