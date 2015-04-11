package uk.co.thefishlive.maths;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

import javafx.application.Application;
import javafx.stage.Stage;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uk.co.thefishlive.auth.AuthHandler;
import uk.co.thefishlive.maths.config.AuthDatabase;
import uk.co.thefishlive.maths.config.SystemSettings;
import uk.co.thefishlive.maths.events.AlertEvent;
import uk.co.thefishlive.maths.events.EventController;
import uk.co.thefishlive.maths.logging.Log4JErrorHandler;
import uk.co.thefishlive.maths.logging.Log4JPrintStream;
import uk.co.thefishlive.maths.resources.ResourceManager;
import uk.co.thefishlive.maths.resources.asset.AssetResourceManager;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.resources.file.FileResourceManager;
import uk.co.thefishlive.maths.resources.jar.JarResourceManager;
import uk.co.thefishlive.maths.ui.loader.UI;
import uk.co.thefishlive.maths.ui.loader.UILoader;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.utils.ProxyUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 */
public class Main extends Application {

    private static final File configDir = new File("config");
    private static Logger logger;

    private static final int STATUS_OK = 0;

    private static RuntimeArgsController argsController;
    private static Main instance;

    private Stage stage;
    private UI currentUI;

    private AuthHandler authHandler;
    private ResourceManager resourceManager;

    private AuthDatabase authDatabase;
    private SystemSettings systemSettings;

    public static void main(String[] args) throws IOException, ResourceException {
        argsController = new RuntimeArgsController();

        if (argsController.parseArgs(args)) {
            return;
        }

        // Set logger config
        System.setProperty("log4j.configurationFile", argsController.getLoggingConfig().getAbsolutePath());
        // Setup Logger
        logger = LogManager.getLogger();

        launch(args);
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void start(Stage stage) {
        try {
            this.loadApplication(stage);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            stage.close();
        }
    }

    public void loadApplication(Stage stage) throws ResourceException, IOException, ReflectiveOperationException, URISyntaxException {
        instance = this;
        logger.info("Starting application");
        logger.debug("");
        logger.debug("-- Java Details --");
        logger.debug("User.Name: {}", System.getProperty("user.name"));
        logger.debug("Java.Version: {}", System.getProperty("java.version"));
        logger.debug("Java.Vendor {}", System.getProperty("java.vendor"));
        logger.debug("Java.Home {}", System.getProperty("java.home"));
        logger.debug("JavaFX.Version: {}", System.getProperty("javafx.runtime.version"));
        logger.debug("OS.Name {}", System.getProperty("os.name"));
        logger.debug("OS.Version {}", System.getProperty("os.version"));
        logger.debug("OS.Arch {}",           System.getProperty("os.arch"));
        logger.info("");

        // Setup logging redirects
        System.setOut(new Log4JPrintStream(System.out, LogManager.getLogger("SysOut"), Level.INFO));
        System.setErr(new Log4JPrintStream(System.err, LogManager.getLogger("SysErr"), Level.WARN));

        Thread.setDefaultUncaughtExceptionHandler(new Log4JErrorHandler());

        // Setup Resource Manager
        this.resourceManager = createResourceManager();
        this.systemSettings = new SystemSettings(new File(configDir, "system.dat"));
        this.systemSettings.load();

        // Setup Auth Handler
        this.authHandler = new MeteorAuthHandler(ProxyUtils.getSystemProxy(), systemSettings.getClientId());
        this.authHandler.addSessionListener(newSession -> {
            authDatabase.updateSession(newSession);
            authDatabase.save();
        });

        // Setup login config
        this.authDatabase = new AuthDatabase(new File(configDir, "auth.dat"));
        this.authDatabase.load();

        // Setup JavaFX
        this.stage = stage;
        this.stage.setResizable(false);
        this.stage.setOnCloseRequest(event -> {
            try {
                logger.info("Exiting application");
                stop();
                System.exit(STATUS_OK);
            } catch (Exception e) {
                Throwables.propagate(e);
            }
        });
        this.stage.setTitle("BS Maths");
        this.stage.show();

        // Setup UI Loader
        UILoader.registerStyleSheet(resourceManager.getResource("style/style.css"));

        // Load starting UI
        boolean loggedin = false;
        String uiResource = "ui/login.fxml";

        if (this.authHandler.getActiveSession() != null) {
            loggedin = true;
            uiResource = "ui/user_main.fxml";
        }

        UI ui = UILoader.loadUI(resourceManager.getResource(uiResource));
        setCurrentUI(ui);

        if (loggedin) {
            EventController.getInstance().postEvent(new AlertEvent("Successfully logged in as " + getAuthHandler().getActiveSession().getProfile().getDisplayName()));
        }
    }

    private ResourceManager createResourceManager() throws ResourceException,
                                                           ReflectiveOperationException {

        Class<? extends ResourceManager> managerClass = argsController.getResourceManagerClass();
        ResourceManager manager = null;
        logger.info("Loading resource manager of type " + managerClass.getName());

        if (managerClass.equals(AssetResourceManager.class)) {
            manager = managerClass.getConstructor(File.class, File.class).newInstance(argsController.getResourceManagerAssetIndex(), argsController.getResourceManagerBaseDir());
        } else if (managerClass.equals(FileResourceManager.class)) {
            manager = managerClass.getConstructor(File.class).newInstance(argsController.getResourceManagerBaseDir());
        } else if (managerClass.equals(JarResourceManager.class)) {
            manager = managerClass.getConstructor().newInstance();
        } else {
            throw new ResourceException("Cannot handle resource manager class");
        }

        return manager;
    }

    public AuthHandler getAuthHandler() {
        return this.authHandler;
    }

    public ResourceManager getResourceManager() {
        return this.resourceManager;
    }

    public AuthDatabase getAuthDatabase() {
        return this.authDatabase;
    }

    public SystemSettings getSystemSettings() {
        return this.systemSettings;
    }

    public void setCurrentUI(UI ui) {
        Preconditions.checkNotNull(ui, "Cannot display a null ui");

        if (currentUI != null)  {
            logger.info("Closing UI {}", currentUI.getName());
            this.currentUI.onClose();
            ui.setParent(this.currentUI);
        }

        logger.info("Displaying UI {}", ui.getName());
        this.currentUI = ui;
        ui.onDisplay();

        this.stage.setScene(ui.buildScene());
        logger.info("UI displayed successfully {}", ui.getName());
    }

    public UI getCurrentUI() {
        return this.currentUI;
    }
}
