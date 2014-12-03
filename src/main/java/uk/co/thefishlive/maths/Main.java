package uk.co.thefishlive.maths;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import uk.co.thefishlive.auth.AuthHandler;
import uk.co.thefishlive.maths.resources.ResourceManager;
import uk.co.thefishlive.maths.resources.file.FileResourceManager;
import uk.co.thefishlive.maths.ui.UILoader;
import uk.co.thefishlive.maths.utils.ProxyUtils;
import uk.co.thefishlive.meteor.MeteorAuthHandler;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 *
 */
public class Main extends Application {

    private static Main instance;
    private Stage stage;
    private AuthHandler authHandler;
    private ResourceManager resourceManager;

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void start(Stage stage) throws Exception {
        instance = this;
        this.stage = stage;
        this.authHandler = new MeteorAuthHandler(ProxyUtils.getSystemProxy());
        this.resourceManager = new FileResourceManager(new File("src/main/resources/"));

        Pane pane = UILoader.loadUI(resourceManager.getResource("ui/login.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public Stage getStage() {
        return this.stage;
    }

    public AuthHandler getAuthHandler() {
        return this.authHandler;
    }
}
