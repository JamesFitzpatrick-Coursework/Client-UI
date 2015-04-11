package uk.co.thefishlive.maths.ui.controllers.admin;

import com.google.common.base.Throwables;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import uk.co.thefishlive.auth.group.Group;
import uk.co.thefishlive.auth.group.GroupProfile;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.events.AlertEvent;
import uk.co.thefishlive.maths.events.EventController;
import uk.co.thefishlive.maths.tasks.TaskManager;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.meteor.group.MeteorGroupProfile;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 */
public class GroupEditController extends Controller {

    @FXML private Pane pnlContainer;

    @FXML private TextField txtDisplayname;
    @FXML private TextField txtGroupname;

    private GroupProfile group;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setGroup(GroupProfile user) {
        this.group = user;
    }

    @Override
    public void onDisplay() {
        if (this.group == null) {
            return;
        }

        // Update with current group values
        this.txtDisplayname.setText(this.group.getDisplayName());
        this.txtGroupname.setText(this.group.getName());
    }

    @FXML
    public void btnCancel_Click(ActionEvent event) {
        this.close();
    }

    @FXML
    public void btnEdit_Click(ActionEvent event) {
        showLoadingAnimation();

        TaskManager.runTaskAsync(() -> {
            try {
                Group group = Main.getInstance().getAuthHandler().getGroupManager().getGroupProfile(this.group);

                GroupProfile updated = new MeteorGroupProfile(this.txtGroupname.getText(), this.txtDisplayname.getText());
                TaskManager.runTaskSync(() -> {
                    group.updateProfile(updated);

                    close();
                    EventController.getInstance().postEvent(new AlertEvent("Updated group " + this.group.getDisplayName()));
                });
            } catch (IOException e) {
                Throwables.propagate(e);
            } finally {
                hideLoadingAnimation();
            }
        });

    }

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }
}
