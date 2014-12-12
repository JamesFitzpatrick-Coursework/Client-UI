package uk.co.thefishlive.maths.ui.login;

import com.google.common.base.Throwables;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.UILoader;
import uk.co.thefishlive.maths.ui.utils.EffectsUtils;
import uk.co.thefishlive.meteor.data.LoginProfile;
import uk.co.thefishlive.meteor.login.exception.LoginException;

import javax.swing.Action;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 */
public class LoginController implements Initializable {

    @FXML private Pane pnlLogin;

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
            Main.getInstance().setCurrentSession(session);

            Pane pane = UILoader.loadUI(Main.getInstance().getResourceManager().getResource("ui/user_main.fxml"));
            Scene scene = new Scene(pane);
            Main.getInstance().getStage().setScene(scene);
        } catch (HttpException ex) {
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
