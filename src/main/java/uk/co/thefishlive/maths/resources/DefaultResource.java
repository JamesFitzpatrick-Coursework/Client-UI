package uk.co.thefishlive.maths.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by James on 03/12/2014.
 */
public class DefaultResource implements Resource {
    private final URL url;

    public DefaultResource(URL url) {
        this.url = url;
    }

    @Override
    public InputStream getContent() throws IOException {
        return url.openStream();
    }

    @Override
    public String getPath() {
        return url.getPath();
    }

    @Override
    public URL getUrl() {
        return url;
    }
}
