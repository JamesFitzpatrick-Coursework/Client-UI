package uk.co.thefishlive.maths.ui.controllers.admin;

import com.google.common.base.Throwables;

import java.io.IOException;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import uk.co.thefishlive.auth.user.User;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.ui.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import uk.co.thefishlive.meteor.user.MeteorUserProfile;

/**
 *
 */
public class UserEditController extends Controller {

    @FXML private TextField txtDisplayname;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtPassword2;

    @FXML private Pane pnlMenu;
    @FXML private Pane pnlContainer;

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
    public void btnMenu_Click(MouseEvent event) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), pnlMenu);
        transition.setByX(205);
        transition.play();
    }

    @FXML
    public void btnCancel_Click(ActionEvent event) {
        Main.getInstance().setCurrentUI(getParent());
    }

    @FXML
    public void btnEdit_Click(ActionEvent event) {
        try {
            User user = Main.getInstance().getAuthHandler().getUserManager().getUserProfile(this.user);

            UserProfile updated = new MeteorUserProfile(this.txtUsername.getText(), this.txtDisplayname.getText());
            user.updateProfile(updated);

            // TODO update password

            Main.getInstance().setCurrentUI(getParent());
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }
}
