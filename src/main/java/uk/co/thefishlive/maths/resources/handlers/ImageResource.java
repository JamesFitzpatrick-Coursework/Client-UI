package uk.co.thefishlive.maths.resources.handlers;

import javafx.scene.image.Image;
import uk.co.thefishlive.maths.resources.exception.ResourceException;

import java.net.URL;

/**
 *
 */
public class ImageResource extends AbstractResource {
    public ImageResource(String path, URL url) {
        super(path, url);
    }

    public Image getImage() throws ResourceException {
        return new Image(getContent());
    }
}
