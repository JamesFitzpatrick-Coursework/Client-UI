package uk.co.thefishlive.maths.ui.admin;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.ui.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 */
public class UserEditController extends Controller {

    @FXML private TextField txtDisplayName;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtPassword2;

    private UserProfile user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    @Override
    public void onDisplay() {
        this.txtDisplayName.setText(this.user.getDisplayName());
        this.txtUsername.setText(this.user.getName());
    }

    @FXML
    public void btnCancel_Click(MouseEvent event) {
        Main.getInstance().setCurrentUI(getParent());
    }

    @FXML
    public void btnEdit_Click(MouseEvent event) {
    }
}
