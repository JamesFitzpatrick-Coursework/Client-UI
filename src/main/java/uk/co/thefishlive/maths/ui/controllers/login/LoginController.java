package uk.co.thefishlive.maths.ui.controllers.login;

import com.google.common.base.Throwables;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.auth.login.LoginHandler;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.http.exception.HttpException;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.events.AlertEvent;
import uk.co.thefishlive.maths.events.EventController;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.tasks.TaskManager;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.maths.ui.loader.UI;
import uk.co.thefishlive.maths.ui.loader.UILoader;
import uk.co.thefishlive.meteor.user.MeteorUserProfile;
import uk.co.thefishlive.auth.login.exception.LoginException;

import java.io.IOException;
import java.net.URL;
import java.security.acl.LastOwnerException;
import java.util.ResourceBundle;

/**
 *
 */
public class LoginController extends Controller {

    private static final Logger logger = LogManager.getLogger();
    private static final Marker markerLogin = MarkerManager.getMarker("LOGIN");

    @FXML
    private Pane pnlContainer;

    @FXML
    private Pane pnlLogin;

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblErrorUsername;
    @FXML
    private Label lblErrorPassword;

    @FXML
    private Button btnLogin;

    @FXML
    public void btnLogin_Click(ActionEvent event) {
        // Show the loading animation
        showLoadingAnimation();

        // Hide the error messages
        lblErrorUsername.setVisible(false);
        lblErrorPassword.setVisible(false);

        boolean valid = true;

        // Check username and password are valid
        if (txtUsername.getText().length() == 0) {
            lblErrorUsername.setText("Please enter your username");
            lblErrorUsername.setVisible(true);
            valid = false;
        }
        if (txtPassword.getText().length() == 0) {
            lblErrorPassword.setText("Please enter your password");
            lblErrorPassword.setVisible(true);
            valid = false;
        }

        // Exit if not valid
        if (!valid) {
            hideLoadingAnimation();
            return;
        }

        logger.info(markerLogin, "Logging in as " + txtUsername.getText());

        // Create login task
        TaskManager.runTaskAsync("Login worker", () -> {
            try {
                // Create User Profile
                UserProfile profile = new MeteorUserProfile(txtUsername.getText());
                LoginHandler handler = Main.getInstance().getAuthHandler().getLoginHandler();

                // Send login request
                Session session = handler.login(profile, txtPassword.getText().toCharArray());
                logger.info(markerLogin, "Successfully logged in as {}", session.getProfile());

                // Update active session
                Main.getInstance().getAuthHandler().setActiveSession(session);

                // Update the UI to the landing page
                TaskManager.runTaskSync(() -> {
                    try {
                        // Load the user landing page UI
                        UI ui = UILoader.loadUI(Main.getInstance().getResourceManager().getResource("ui/user_main.fxml"));
                        Main.getInstance().setCurrentUI(ui);

                        // Send a new alert with successful login message
                        EventController.getInstance().postEvent(new AlertEvent("Successfully logged in as " + session.getProfile().getDisplayName()));
                    } catch (IOException | ResourceException e) {
                        Throwables.propagate(e);
                    }
                });
            } catch (LoginException | IOException ex) {
                Platform.runLater(() -> {
                    logger.error(markerLogin, "Error logging into account", ex);

                    // Display the error with login
                    if (ex.getMessage().contains("password")) {
                        lblErrorPassword.setText(ex.getMessage());
                        lblErrorPassword.setVisible(true);
                        return;
                    }
                    if (ex.getMessage().contains("user")) {
                        lblErrorUsername.setText(ex.getMessage());
                        lblErrorUsername.setVisible(true);
                        return;
                    }
                });
            } finally {
                // Hide the loading animation on completion
                hideLoadingAnimation();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Fade the login panel in
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), pnlLogin);
        fadeIn.setFromValue(0.5d);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }
}
