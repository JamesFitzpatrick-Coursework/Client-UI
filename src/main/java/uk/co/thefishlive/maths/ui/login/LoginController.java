package uk.co.thefishlive.maths.ui.login;

import com.google.common.base.Throwables;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import uk.co.thefishlive.auth.data.Profile;
import uk.co.thefishlive.auth.login.LoginHandler;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.http.exception.HttpException;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.meteor.data.LoginProfile;
import uk.co.thefishlive.meteor.login.exception.LoginException;

import java.io.IOException;

/**
 *
 */
public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    @FXML private Label lblErrorUsername;
    @FXML private Label lblErrorPassword;

    @FXML private Button btnLogin;

    @FXML private Pane pnlAlert;
    @FXML private Label lblAlertMessage;

    @FXML
    public void btnLogin_Click(ActionEvent event) {
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

        System.out.println("Logging in");

        try {
            Profile profile = new LoginProfile(txtUsername.getText());
            LoginHandler handler = Main.getInstance().getAuthHandler().getLoginHandler();
            Session session = handler.login(profile, txtPassword.getText().toCharArray());
            System.out.println(session.getOwner());

            pnlAlert.setVisible(true);
            lblAlertMessage.setText("Successfully logged in as " + session.getOwner().getDisplayName());

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), pnlAlert);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1.0);
            fadeIn.play();

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pnlAlert);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setDelay(Duration.seconds(5));
            fadeOut.play();
        } catch (HttpException ex) {
            if (ex.getMessage().contains("password")) {
                lblErrorPassword.setText(ex.getMessage());
            }
            if (ex.getMessage().contains("username")) {
                lblErrorUsername.setText(ex.getMessage());
            }
        } catch (LoginException | IOException ex) {
            Throwables.propagate(ex);
        }
    }

    @FXML
    public void txtUsername_KeyTyped(KeyEvent event) {
    }

    @FXML
    public void txtPassword_KeyTyped(KeyEvent event) {
    }
}
