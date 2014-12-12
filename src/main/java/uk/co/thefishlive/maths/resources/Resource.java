package uk.co.thefishlive.maths.resources;

import uk.co.thefishlive.maths.resources.exception.ResourceException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Represents a resource located on the client machine loaded by a
 * {@link ResourceManager}.
 *
 * @see ResourceManager
 */
public interface Resource {

    /**
     * Get the content of this resource.
     *
     * @return a {@link InputStream} containing the content of this resource
     * @throws ResourceException signals an error has occurred loading the
     *                              resource content
     */
    public InputStream getContent() throws ResourceException;

    /**
     * Get the relative path to this resource, used to identify this
	 * resource at runtime.
     *
     * @return the relative path of this resource
     */
    public String getPath();

    /**
     * Get the encoded url of this resource for the client's machine.
     * <p />
     * Note this is machine dependent so shouldn't be used to identify
     * resources {@link #getPath()} should be used instead.
     *
     * @return the url of this resource
	 * @see #getPath()
     * @throws ResourceException signals an error has occurred getting the
     *                              resource url
     */
    public URL getUrl() throws ResourceException;

}
