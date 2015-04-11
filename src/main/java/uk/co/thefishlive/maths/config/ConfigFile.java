package uk.co.thefishlive.maths.config;

import java.io.File;

/**
 * Represents a configuration file used to store information about the current
 * state of the application on disk.
 */
public interface ConfigFile {

    /**
     * Get the file this config saves to.
     *
     * @return the file this config saves to
     */
    public File getFile();

    /**
     * Load this config from disk.
     */
    public void load();

    /**
     * Save this config to disk.
     */
    public void save();

}
