package uk.co.thefishlive.maths;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import uk.co.thefishlive.auth.AuthHandler;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.auth.session.SessionListener;
import uk.co.thefishlive.maths.config.AuthDatabase;
import uk.co.thefishlive.maths.config.SystemSettings;
import uk.co.thefishlive.maths.events.AlertEvent;
import uk.co.thefishlive.maths.events.EventController;
import uk.co.thefishlive.maths.logging.Log4JErrorHandler;
import uk.co.thefishlive.maths.logging.Log4JPrintStream;
import uk.co.thefishlive.maths.resources.ResourceManager;
import uk.co.thefishlive.maths.resources.file.FileResourceManager;
import uk.co.thefishlive.maths.ui.loader.UI;
import uk.co.thefishlive.maths.ui.loader.UILoader;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.utils.ProxyUtils;

import java.io.File;
import java.io.IOException;

/**
 *
 */
public class Main extends Application {

    private static final Logger logger = LogManager.getLogger();

    private static Main instance;

    private Stage stage;
    private UI currentUI;

    private AuthHandler authHandler;
    private ResourceManager resourceManager;

    private AuthDatabase authDatabase;
    private SystemSettings systemSettings;

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void start(Stage stage) throws Exception {
        instance = this;
        logger.info("Starting application");

        // Setup logging redirects
        System.setOut(new Log4JPrintStream(System.out, LogManager.getLogger("SysOut"), Level.INFO));
        System.setErr(new Log4JPrintStream(System.err, LogManager.getLogger("SysErr"), Level.WARN));

        Thread.setDefaultUncaughtExceptionHandler(new Log4JErrorHandler());

        // Setup Resource Manager
        this.resourceManager = new FileResourceManager(new File("classes"));
        this.systemSettings = new SystemSettings(new File("config/system.dat"));
        this.systemSettings.load();

        // Setup Auth Handler
        this.authHandler = new MeteorAuthHandler(ProxyUtils.getSystemProxy(), systemSettings.getClientId());
        this.authHandler.addSessionListener(new SessionListener() {
            @Override
            public void onActiveSessionChanged(Session newSession) {
                authDatabase.updateSession(newSession);
                authDatabase.save();
            }
        });

        // Setup login config
        this.authDatabase = new AuthDatabase(new File("config/auth.dat"));
        this.authDatabase.load();

        // Setup JavaFX
        this.stage = stage;
        this.stage.setResizable(false);
        this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    logger.info("Exiting application");
                    stop();
                    System.exit(0);
                } catch (Exception e) {
                    Throwables.propagate(e);
                }
            }
        });
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
    }

    public UI getCurrentUI() {
        return this.currentUI;
    }
}
