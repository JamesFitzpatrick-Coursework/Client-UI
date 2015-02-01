package uk.co.thefishlive.maths.resources.handlers;

import uk.co.thefishlive.maths.resources.Resource;
import uk.co.thefishlive.maths.resources.exception.ResourceException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 *
 */
public abstract class AbstractResource implements Resource {

    private final URL url;
    private final String path;

    public AbstractResource(String path, URL url) {
        this.url = url;
        this.path = path;
    }

    @Override
    public InputStream getContent() throws ResourceException {
        try {
            return this.url.openStream();
        } catch (IOException e) {
            throw new ResourceException(e);
        }
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public URL getUrl() {
        return this.url;
    }
}
