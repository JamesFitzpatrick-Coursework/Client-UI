package uk.co.thefishlive.maths.ui.controllers.admin;

import com.google.common.base.Throwables;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import uk.co.thefishlive.auth.user.User;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.events.AlertEvent;
import uk.co.thefishlive.maths.events.EventController;
import uk.co.thefishlive.maths.ui.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import uk.co.thefishlive.maths.ui.utils.EffectsUtils;
import uk.co.thefishlive.meteor.user.MeteorUserProfile;

/**
 *
 */
public class UserEditController extends Controller {

    @FXML private Pane pnlContainer;
    @FXML private Pane pnlForm;

    @FXML private TextField txtDisplayname;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtPassword2;

    @FXML private Label lblErrorUsername;
    @FXML private Label lblErrorDisplayname;
    @FXML private Label lblErrorPassword;
    @FXML private Label lblErrorPassword2;

    private UserProfile user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    @Override
    public void onDisplay() {
        if (this.user == null) {
            return;
        }

        this.txtDisplayname.setText(this.user.getDisplayName());
        this.txtUsername.setText(this.user.getName());
    }

    @FXML
    public void btnCancel_Click(ActionEvent event) {
        Main.getInstance().setCurrentUI(getParent());
    }

    @FXML
    public void btnEdit_Click(ActionEvent event) {
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

        // Only validate the password if we are changing it
        boolean changingPassword = false;

        if (txtPassword.getText().length() > 0) {
            changingPassword = true;

            if (txtPassword.getText().length() <= 5) { // Check for valid length password
                lblErrorPassword.setVisible(true);
                error = true;
            }
            if (!txtPassword.getText().equals(txtPassword2.getText())) { // Validate password against confirmation
                lblErrorPassword2.setVisible(true);
                error = true;
            }
        }

        if (error) {
            // Something went wrong exit now
            EffectsUtils.panelShake(pnlForm);
            return;
        }
        try {
            // Get the user we are trying to update
            User user = Main.getInstance().getAuthHandler().getUserManager().getUserProfile(this.user);

            UserProfile updated = new MeteorUserProfile(this.txtUsername.getText(), this.txtDisplayname.getText());
            user.updateProfile(updated);

            if (changingPassword) {
                // TODO update password
            }

            Main.getInstance().setCurrentUI(getParent());
            EventController.getInstance().postEvent(new AlertEvent("Updated user " + user.getProfile().getDisplayName()));
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }
}
