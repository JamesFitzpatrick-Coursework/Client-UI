package uk.co.thefishlive.maths.ui.loader;

import com.google.common.base.Throwables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.resources.exception.ResourceException;

import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 */
public class UIClassLoader extends URLClassLoader {

    private static final Logger logger = LogManager.getLogger();

    public UIClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    @Override
    public URL findResource(String name) {
        logger.info("Loading resource " + name);

        try {
            return Main.getInstance().getResourceManager().getResource(name).getUrl();
        } catch (ResourceException e) {
            Throwables.propagate(e);
            return null;
        }
    }
}
