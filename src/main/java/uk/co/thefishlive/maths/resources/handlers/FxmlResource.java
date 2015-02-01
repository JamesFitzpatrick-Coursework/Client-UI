package uk.co.thefishlive.maths.resources.handlers;

import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.loader.UI;
import uk.co.thefishlive.maths.ui.loader.UILoader;

import java.io.IOException;
import java.net.URL;

/**
 *
 */
public class FxmlResource extends AbstractResource {
    public FxmlResource(String path, URL url) {
        super(path, url);
    }

    public UI loadUI() throws IOException, ResourceException {
        return UILoader.loadUI(this);
    }
}
