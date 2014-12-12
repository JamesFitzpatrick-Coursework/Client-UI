package uk.co.thefishlive.maths.resources;

import uk.co.thefishlive.maths.resources.exception.ResourceException;

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

}
