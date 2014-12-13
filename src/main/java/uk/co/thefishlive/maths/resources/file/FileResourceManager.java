package uk.co.thefishlive.maths.resources.file;

import uk.co.thefishlive.maths.resources.CachingResourceManger;
import uk.co.thefishlive.maths.resources.Resource;

import java.io.File;
import uk.co.thefishlive.maths.resources.exception.ResourceNotFoundException;

/**
 * A resource manger based off a standard machines file system.
 */
public class FileResourceManager extends CachingResourceManger {

    private final File basedir;

    public FileResourceManager(File basedir) {
        this.basedir = basedir;
    }

    @Override
    protected Resource loadResource(String path) throws ResourceNotFoundException {
        File file = new File(basedir, path);

        if (!file.exists()) {
            throw new ResourceNotFoundException("Could not find resource at " + path);
        }

        return new FileResource(file);
    }

}
