package uk.co.thefishlive.maths.resources;

import java.util.HashMap;
import java.util.Map;
import uk.co.thefishlive.maths.resources.exception.ResourceException;

/**
 * Represents a resource manager that caches the resources loaded in memory.
 */
public abstract class CachingResourceManger implements ResourceManager {

    private Map<String, Resource> cache = new HashMap<>();

    @Override
    public final Resource getResource(String path) throws ResourceException {
        Resource resource = cache.get(path);

        if (resource == null) {
            resource = loadResource(path);
            cache.put(path, resource);
        }

        return resource;
    }

    protected abstract Resource loadResource(String path) throws ResourceException;

}
