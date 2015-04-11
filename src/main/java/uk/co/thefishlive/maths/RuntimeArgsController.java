package uk.co.thefishlive.maths;

import uk.co.thefishlive.maths.resources.Resource;
import uk.co.thefishlive.maths.resources.ResourceManager;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

/**
 *
 */
public class RuntimeArgsController {

    private static final String DEFAULT_RESOURCE_MANAGER = "uk.co.thefishlive.maths.resources.jar.JarResourceManager";
    private static final String DEFAULT_RESOURCE_DATA = "classes";
    private static final String DEFAULT_RESOURCE_INDEX = "index.json";

    private static final String DEFAULT_LOGGING_CONFIG = "log4j2.xml";

    private OptionParser parser = new OptionParser();

    private OptionSpec help;
    private OptionSpec<String> resourceManagerClass;
    private OptionSpec<String> resourceManagerBase;
    private OptionSpec<String> resourceManagerAssetsIndex;
    private OptionSpec<String> loggingConfig;

    private OptionSet set;

    public RuntimeArgsController() {
        // Setup CLI Arguments
        this.help = parser.acceptsAll(Arrays.asList("help", "?", "h")).forHelp();

        this.resourceManagerClass = parser.accepts("resources.manager").withRequiredArg().ofType(String.class).defaultsTo(DEFAULT_RESOURCE_MANAGER);
        this.resourceManagerBase = parser.accepts("resources.manager.base").withRequiredArg().ofType(String.class).defaultsTo(DEFAULT_RESOURCE_DATA);
        this.resourceManagerAssetsIndex = parser.accepts("resources.manager.index").withRequiredArg().ofType(String.class).defaultsTo(DEFAULT_RESOURCE_INDEX);

        this.loggingConfig = parser.accepts("logging.config").withRequiredArg().ofType(String.class).defaultsTo(DEFAULT_LOGGING_CONFIG);
    }

    public boolean parseArgs(String[] args) throws IOException {
        // Parse the arguments provided
        this.set = parser.parse(args);

        // Print help if required
        if (this.set.hasArgument(this.help)) {
            parser.printHelpOn(System.out);
            return true;
        }

        return false;
    }

    public Class<? extends ResourceManager> getResourceManagerClass() throws ClassNotFoundException {
        return Class.forName(resourceManagerClass.value(set)).asSubclass(ResourceManager.class);
    }

    public File getResourceManagerBaseDir() {
        return new File(resourceManagerBase.value(set));
    }

    public File getResourceManagerAssetIndex() {
        return new File(resourceManagerAssetsIndex.value(set));
    }

    public File getLoggingConfig() {
        return new File(loggingConfig.value(set));
    }
}
