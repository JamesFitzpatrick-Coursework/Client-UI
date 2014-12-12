package uk.co.thefishlive.maths.resources.file;

import uk.co.thefishlive.maths.resources.Resource;
import uk.co.thefishlive.maths.resources.exception.ResourceException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by James on 11/12/2014.
 */
public class FileResource implements Resource {
    private final File file;

    public FileResource(File file) {
        this.file = file;
    }

    @Override
    public InputStream getContent() throws ResourceException {
        try {
            return getUrl().openStream();
        } catch (IOException ex) {
            throw new ResourceException(ex);
        }
    }

    @Override
    public String getPath() {
        return file.getPath();
    }

    @Override
    public URL getUrl() throws ResourceException {
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException ex) {
            throw new ResourceException(ex);
        }
    }
}
