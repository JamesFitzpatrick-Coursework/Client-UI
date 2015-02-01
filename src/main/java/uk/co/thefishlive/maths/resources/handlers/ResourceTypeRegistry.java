package uk.co.thefishlive.maths.resources.handlers;

import uk.co.thefishlive.maths.resources.Resource;
import uk.co.thefishlive.maths.resources.exception.ResourceException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ResourceTypeRegistry {

    private Map<String, Class<? extends Resource>> resourceTypes = new HashMap<>();

    public ResourceTypeRegistry() {
        registerResourceType("png",     ImageResource.class);
        registerResourceType("jpg",     ImageResource.class);
        registerResourceType("fxml",    FxmlResource.class);
        registerResourceType("css",     CssResource.class);
    }

    public Resource createResource(String name, URL content) throws ResourceException {
        String ext = name.substring(name.lastIndexOf('.') + 1);

        try {
            Class<? extends Resource> clazz = resourceTypes.get(ext);
            Constructor<? extends Resource> ctor = clazz.getConstructor(String.class, URL.class);

            return ctor.newInstance(name, content);
        } catch (ReflectiveOperationException e) {
            throw new ResourceException(e);
        }
    }

    public void registerResourceType(String extension, Class<? extends Resource> handler) {
        resourceTypes.put(extension, handler);
    }
}
