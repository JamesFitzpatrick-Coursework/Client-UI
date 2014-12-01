package uk.co.thefishlive.maths.ui.login;

import com.google.common.base.Throwables;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import uk.co.thefishlive.auth.data.Profile;
import uk.co.thefishlive.auth.login.LoginHandler;
import uk.co.thefishlive.auth.session.Session;
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
