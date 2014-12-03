package uk.co.thefishlive.maths.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by James on 03/12/2014.
 */
public interface Resource {

    public InputStream getContent() throws IOException;

    public String getPath();

    public URL getUrl();

}
