package uk.co.thefishlive.maths;

import com.google.common.base.Throwables;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.apache.logging.log4j.LogManager;

import uk.co.thefishlive.auth.AuthHandler;
import uk.co.thefishlive.auth.session.Session;
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

    private static Main instance;
    private Stage stage;
    private AuthHandler authHandler;
    private ResourceManager resourceManager;
    private Session session;

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.setOut(new Log4JPrintStream(System.out, LogManager.getLogger("SysOut")));

        instance = this;
        this.stage = stage;
        this.authHandler = new MeteorAuthHandler(ProxyUtils.getSystemProxy());
        this.resourceManager = new FileResourceManager(new File("src/main/resources/"));

        UI ui = UILoader.loadUI(resourceManager.getResource("ui/login.fxml"));
        Scene scene = new Scene(ui.getPane());
        stage.setMaxHeight(628);
        stage.setMaxWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    System.out.println("Exiting application");
                    stop();
                    System.exit(0);
                } catch (Exception e) {
                    Throwables.propagate(e);
                }
            }
        });
    }

    public Stage getStage() {
        return this.stage;
    }

    public AuthHandler getAuthHandler() {
        return this.authHandler;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public Session getCurrentSession() {
        return this.session;
    }

    public void setCurrentSession(Session session) {
        this.session = session;
    }
}
