package uk.co.thefishlive.maths.ui.controllers.admin;

import com.google.common.base.Throwables;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import uk.co.thefishlive.auth.user.User;
import uk.co.thefishlive.auth.user.UserManager;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.events.AlertEvent;
import uk.co.thefishlive.maths.events.EventController;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.meteor.user.MeteorUserProfile;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserCreateController extends Controller {

    @FXML private Pane pnlContainer;

    @FXML private TextField txtUsername;
    @FXML private TextField txtDisplayname;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtPassword2;

    @FXML private Label lblErrorUsername;
    @FXML private Label lblErrorDisplayname;
    @FXML private Label lblErrorPassword;
    @FXML private Label lblErrorPassword2;

    private CreateCallback callback;

    @FXML
    public void btnCancel_Click(ActionEvent event) {
        Main.getInstance().setCurrentUI(getParent());
    }

    @FXML
    public void btnCreate_Click(ActionEvent event) {
        // Reset error messages
        lblErrorUsername.setVisible(false);
        lblErrorDisplayname.setVisible(false);
        lblErrorPassword.setVisible(false);
        lblErrorPassword2.setVisible(false);

        boolean error = false;

        // Validate input
        if (txtUsername.getText().length() <= 0) { // Check for username
            lblErrorUsername.setVisible(true);
            error = true;
        }
        if (txtUsername.getText().contains(" ")) { // Validate username
            lblErrorUsername.setText("Username must not contain spaces");
            lblErrorUsername.setVisible(true);
            error = true;
        }
        if (txtDisplayname.getText().length() <= 0) { // Check for display name
            lblErrorDisplayname.setVisible(true);
            error = true;
        }
        if (txtPassword.getText().length() <= 5) { // Check for valid length password
            lblErrorPassword.setVisible(true);
            error = true;
        }
        if (!txtPassword.getText().equals(txtPassword2.getText())) { // Validate password against confirmation
            lblErrorPassword2.setVisible(true);
            error = true;
        }

        if (error) {
            // Something went wrong exit now
            return;
        }

        // Create new user
        UserProfile profile = new MeteorUserProfile(txtUsername.getText(), txtDisplayname.getText());
        UserManager manager = Main.getInstance().getAuthHandler().getUserManager();

        try {
            // Create the user
            profile = manager.createUser(profile, txtPassword.getText().toCharArray());

            // Get the new created user
            User user = manager.getUserProfile(profile);
            callback.userCreated(user);
            // Close the current UI
            Main.getInstance().setCurrentUI(this.getParent());

            // Alert the user
            EventController.getInstance().postEvent(new AlertEvent("Created user " + profile.getDisplayName()));
        } catch (IOException e) {
            // ERROR
            Throwables.propagate(e);
        }
    }

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }

    public void setCreateCallback(CreateCallback callback) {
        this.callback = callback;
    }

    public static interface CreateCallback {
        public void userCreated(User profile);
    }
}
