package uk.co.thefishlive.maths.resources;

import java.util.HashMap;
import java.util.Map;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.resources.handlers.ResourceTypeRegistry;

/**
 * Represents a resource manager that caches the resources loaded in memory.
 */
public abstract class CachingResourceManger implements ResourceManager {

    protected ResourceTypeRegistry typeRegistry;
    private Map<String, Resource> cache = new HashMap<>();

    public CachingResourceManger() {
        typeRegistry = new ResourceTypeRegistry();
    }

    @Override
    public final Resource getResource(String path) throws ResourceException {
        Resource resource = cache.get(path);

        if (resource == null) {
            resource = loadResource(path);
            cache.put(path, resource);
        }

        return resource;
    }

    public final <T extends Resource> T getResourceAs(String path, Class<T> type) throws ResourceException {
        return (T) getResource(path);
    }

    protected abstract Resource loadResource(String path) throws ResourceException;

}
