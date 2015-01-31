package uk.co.thefishlive.maths;

import com.google.common.base.Throwables;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
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
import uk.co.thefishlive.maths.logging.Log4JErrorHandler;
import uk.co.thefishlive.maths.logging.Log4JPrintStream;
import uk.co.thefishlive.maths.resources.ResourceManager;
import uk.co.thefishlive.maths.resources.file.FileResourceManager;
import uk.co.thefishlive.maths.ui.UI;
import uk.co.thefishlive.maths.ui.UILoader;
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
        this.resourceManager = new FileResourceManager(new File("src/main/resources/"));
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

        // Load starting UI
        String uiResource = "ui/login.fxml";

        if (this.authHandler.getActiveSession() != null) {
            uiResource = "ui/user_main.fxml";
        }

        UI ui = UILoader.loadUI(resourceManager.getResource(uiResource));
        setCurrentUI(ui);

        this.stage.show();
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

    public void setCurrentUI(UI ui) {
        if (currentUI != null) logger.info("Closing UI {}", currentUI.getName());
        logger.info("Displaying UI {}", ui.getName());

        ui.setParent(this.currentUI);
        this.currentUI = ui;
        ui.onDisplay();
        this.stage.setScene(ui.buildScene());
    }

    public UI getCurrentUI() {
        return this.currentUI;
    }
}
