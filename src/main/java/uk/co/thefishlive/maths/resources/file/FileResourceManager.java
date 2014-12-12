package uk.co.thefishlive.maths.resources.file;

import java.util.Map;
import uk.co.thefishlive.maths.resources.Resource;
import uk.co.thefishlive.maths.resources.ResourceManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.resources.exception.ResourceNotFoundException;

/**
 * A resource manger based off a standard machines file system.
 */
public class FileResourceManager implements ResourceManager {

    private final File basedir;
    private Map<String, Resource> resourceCache;

    public FileResourceManager(File basedir) {
        this.basedir = basedir;
    }

    @Override
    public Resource getResource(String path) throws ResourceException {
        Resource resource = resourceCache.get(path);

        if (resource == null) {
            resource = loadResource(path);
            resourceCache.put(path, resource);
        }

        return resource;
    }

    private Resource loadResource(String path) throws ResourceNotFoundException {
        File file = new File(basedir, path);

        if (!file.exists()) {
            throw new ResourceNotFoundException("Could not find resource at " + path);
        }

        return new FileResource(file);
    }

}
