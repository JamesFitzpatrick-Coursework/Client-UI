package uk.co.thefishlive.maths.resources.file;

import uk.co.thefishlive.maths.resources.DefaultResource;
import uk.co.thefishlive.maths.resources.Resource;
import uk.co.thefishlive.maths.resources.ResourceManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

/**
 * Created by James on 03/12/2014.
 */
public class FileResourceManager implements ResourceManager {

    private final File basedir;

    public FileResourceManager(File basedir) {
        this.basedir = basedir;
    }

    @Override
    public Resource getResource(String path) throws FileNotFoundException, MalformedURLException {
        File resource = new File(basedir, path);

        if (!resource.exists()) {
            throw new FileNotFoundException("Could not find resource at " + path);
        }

        return new DefaultResource(resource.toURI().toURL());
    }

}
