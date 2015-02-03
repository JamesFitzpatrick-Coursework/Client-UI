package uk.co.thefishlive.maths.ui.controllers.admin;

import com.google.common.base.Throwables;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import uk.co.thefishlive.auth.group.Group;
import uk.co.thefishlive.auth.group.GroupManager;
import uk.co.thefishlive.auth.group.GroupProfile;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.events.AlertEvent;
import uk.co.thefishlive.maths.events.EventController;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.meteor.group.MeteorGroupProfile;

public class GroupCreateController extends Controller {
    private CreateCallback callback;

    @FXML private TextField txtGroupname;
    @FXML private TextField txtDisplayname;

    @FXML private Label lblErrorGroupname;
    @FXML private Label lblErrorDisplayname;

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
        if (txtGroupname.getText().length() <= 0) {
            lblErrorGroupname.setVisible(true);
            error = true;
        }
        if (txtDisplayname.getText().length() <= 0) {
            lblErrorDisplayname.setVisible(true);
            error = true;
        }

        if (error) {
            // Something went wrong exit now
            return;
        }

        // Create new user
        GroupProfile profile = new MeteorGroupProfile(txtGroupname.getText(), txtDisplayname.getText());
        GroupManager manager = Main.getInstance().getAuthHandler().getGroupManager();

        try {
            profile = manager.createGroup(profile);

            Group group = manager.getGroupProfile(profile);
            callback.groupCreated(group);
            Main.getInstance().setCurrentUI(this.getParent());
            EventController.getInstance().postEvent(new AlertEvent("Created group " + profile.getDisplayName()));
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
        public void groupCreated(Group profile);
    }
}
