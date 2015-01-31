package uk.co.thefishlive.maths.ui.admin;

import com.google.common.base.Throwables;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.maths.ui.UI;
import uk.co.thefishlive.meteor.user.MeteorUserProfile;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserCreateController extends Controller {
    private CreateCallback callback;

    @FXML private TextField txtUsername;
    @FXML private TextField txtDisplayname;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtPassword2;

    @FXML private Label lblErrorUsername;
    @FXML private Label lblErrorDisplayname;
    @FXML private Label lblErrorPassword;
    @FXML private Label lblErrorPassword2;

    @FXML private Pane pnlMenu;
    @FXML private Pane pnlContainer;

    @FXML
    public void btnMenu_Click(MouseEvent event) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), pnlMenu);
        transition.setToX(0);
        transition.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void btnCancel_Click(ActionEvent event) {
        Main.getInstance().setCurrentUI(getParent());
    }

    @FXML
    public void btnCreate_Click(ActionEvent event) {
        boolean error = false;

        // Validate input
        if (txtUsername.getText().length() <= 0) {
            lblErrorUsername.setVisible(true);
            error = true;
        }
        if (txtDisplayname.getText().length() <= 0) {
            lblErrorDisplayname.setVisible(true);
            error = true;
        }
        if (txtPassword.getText().length() <= 5) {
            lblErrorPassword.setVisible(true);
            error = true;
        }
        if (!txtPassword.getText().equals(txtPassword2.getText())) {
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
            profile = manager.createUser(profile, txtPassword.getText().toCharArray());

            User user = manager.getUserProfile(profile);
            callback.userCreated(user);
            Main.getInstance().setCurrentUI(this.getParent());
        } catch (IOException e) {
            // ERROR
            Throwables.propagate(e);
        }

    }

    public void setCreateCallback(CreateCallback callback) {
        this.callback = callback;
    }

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }

    public static interface CreateCallback {
        public void userCreated(User profile);
    }
}
