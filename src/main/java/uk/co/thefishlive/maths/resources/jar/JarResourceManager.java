package uk.co.thefishlive.maths.resources.jar;

import uk.co.thefishlive.maths.resources.CachingResourceManger;
import uk.co.thefishlive.maths.resources.Resource;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.resources.exception.ResourceNotFoundException;

import java.net.URL;

public class JarResourceManager extends CachingResourceManger {
    @Override
    protected Resource loadResource(String path) throws ResourceException {
        URL url = JarResourceManager.class.getResource("/" + path);

        if (url == null) {
            throw new ResourceNotFoundException(path);
        }

        return typeRegistry.createResource(path, url);
    }
}
