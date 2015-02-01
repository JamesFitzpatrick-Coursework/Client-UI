package uk.co.thefishlive.maths.resources;

import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.resources.handlers.ImageResource;

public interface ResourceManager {

    /**
     * Get the information about a specified resource or load it if it hasn't
     * already been loaded this session.
     *
     * @param path the relative path to this resource
     * @return the resource specified
     * @throws ResourceException signals that something went wrong loading the
     *                              resource
     */
    public Resource getResource(String path) throws ResourceException;

    /**
     * Get the information about a specified resource or load it if it hasn't
     * already been loaded this session, and then casts it to the specified
     * type.
     *
     * @param path the relative path to this resource
     * @param type the resource type to cast it to
     * @return the resource specified
     * @throws ResourceException signals that something went wrong loading the
     *                              resource
     */
    public <T extends Resource> T getResourceAs(String path, Class<T> type) throws ResourceException;
}
