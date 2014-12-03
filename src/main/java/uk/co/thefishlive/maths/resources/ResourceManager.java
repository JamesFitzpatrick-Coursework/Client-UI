package uk.co.thefishlive.maths.resources;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

/**
 * Created by James on 03/12/2014.
 */
public interface ResourceManager {

    public Resource getResource(String path) throws FileNotFoundException, MalformedURLException;

}
