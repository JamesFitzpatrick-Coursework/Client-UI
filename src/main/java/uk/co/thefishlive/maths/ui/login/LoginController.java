package uk.co.thefishlive.maths.ui.login;

import com.google.common.base.Throwables;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.http.exception.HttpException;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.maths.ui.UI;
import uk.co.thefishlive.maths.ui.UILoader;
import uk.co.thefishlive.maths.ui.utils.EffectsUtils;
import uk.co.thefishlive.meteor.user.MeteorUserProfile;
import uk.co.thefishlive.meteor.login.exception.LoginException;
import uk.co.thefishlive.meteor.user.MeteorUserProfile;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;

/**
 *
 */
public class LoginController extends Controller {

    private static final Logger logger = LogManager.getLogger();
    private static final Marker markerLogin = MarkerManager.getMarker("LOGIN");

    @FXML private Pane pnlContainer;

    @FXML private Pane pnlLogin;

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    @FXML private Label lblErrorUsername;
    @FXML private Label lblErrorPassword;

    @FXML private Button btnLogin;

    @FXML
    public void btnLogin_Click(ActionEvent event) {
        try {
            showLoadingAnimation(pnlContainer);
        } catch (ResourceException | IOException e) {
            Throwables.propagate(e);
        }

        boolean valid = true;

        if (txtUsername.getText().length() == 0) {
            lblErrorUsername.setText("Please enter your username");
            valid = false;
        }
        if (txtPassword.getText().length() == 0) {
            lblErrorPassword.setText("Please enter your password");
            valid = false;
        }

        if (!valid) {
            return;
        }

        logger.info(markerLogin, "Logging in as " + txtUsername.getText());

        try {
            UserProfile profile = new MeteorUserProfile(txtUsername.getText());
            LoginHandler handler = Main.getInstance().getAuthHandler().getLoginHandler();
            Session session = handler.login(profile, txtPassword.getText().toCharArray());
            logger.info(markerLogin, "Successfully logged in as {}", session.getProfile());
            Main.getInstance().getAuthHandler().setActiveSession(session);

            UI ui = UILoader.loadUI(Main.getInstance().getResourceManager().getResource("ui/user_main.fxml"));
            Main.getInstance().setCurrentUI(ui);
        } catch (HttpException ex) {
            logger.error(markerLogin, "Error logging into account", ex);

            if (ex.getMessage().contains("password")) {
                lblErrorPassword.setText(ex.getMessage());
            }
            if (ex.getMessage().contains("username")) {
                lblErrorUsername.setText(ex.getMessage());
            }
        } catch (LoginException | IOException | ResourceException ex) {
            Throwables.propagate(ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), pnlLogin);
        fadeIn.setFromValue(0.5d);
        fadeIn.setToValue(1);
        fadeIn.play();
    }
}
