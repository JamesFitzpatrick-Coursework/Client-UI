package uk.co.thefishlive.maths.config;

import java.io.File;

/**
 *
 */
public interface ConfigFile {

    public File getFile();

    public void load();

    public void save();

}
