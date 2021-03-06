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
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.tasks.Task;
import uk.co.thefishlive.maths.tasks.TaskManager;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.meteor.group.MeteorGroupProfile;

public class GroupCreateController extends Controller {
    private CreateCallback callback;

    @FXML private Pane pnlContainer;

    @FXML private TextField txtGroupname;
    @FXML private TextField txtDisplayname;

    @FXML private Label lblErrorGroupname;
    @FXML private Label lblErrorDisplayname;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void btnCancel_Click(ActionEvent event) {
        Main.getInstance().setCurrentUI(getParent());
    }

    @FXML
    public void btnCreate_Click(ActionEvent event) {
        showLoadingAnimation();
        lblErrorGroupname.setVisible(false);
        lblErrorDisplayname.setVisible(false);

        boolean error = false;

        // Validate input
        if (txtGroupname.getText().length() <= 0) { // Check groupname length
            lblErrorGroupname.setVisible(true);
            error = true;
        }
        if (txtGroupname.getText().contains(" ")) { // Validate groupname
            lblErrorGroupname.setVisible(true);
            lblErrorGroupname.setText("Classname cannot contain spaces");
            error = true;
        }
        if (txtDisplayname.getText().length() <= 0) { // Check displayname length
            lblErrorDisplayname.setVisible(true);
            error = true;
        }

        if (error) {
            // Never trust the user
            hideLoadingAnimation();
            return;
        }

        // Create new user
        GroupProfile profile = new MeteorGroupProfile(txtGroupname.getText(), txtDisplayname.getText());
        GroupManager manager = Main.getInstance().getAuthHandler().getGroupManager();

        // Create the group off the main thread
        TaskManager.runTaskAsync("Create Group", () -> {
            try {
                // Send the creation request
                GroupProfile createdProfile = manager.createGroup(profile);

                Group group = manager.getGroupProfile(createdProfile);
                TaskManager.runTaskSync(() -> {
                    callback.groupCreated(group);

                    // Display the success back to the user
                    Main.getInstance().setCurrentUI(getParent());
                    EventController.getInstance().postEvent(new AlertEvent("Created group " + createdProfile.getDisplayName()));
                });
            } catch (IOException e) {
                // ERROR
                Throwables.propagate(e);
            } finally {
                hideLoadingAnimation();
            }
        });
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
