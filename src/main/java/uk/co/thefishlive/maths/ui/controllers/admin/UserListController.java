package uk.co.thefishlive.maths.ui.controllers.admin;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.google.common.base.Throwables;

import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uk.co.thefishlive.auth.group.GroupProfile;
import uk.co.thefishlive.auth.group.member.GroupMemberProfile;
import uk.co.thefishlive.auth.user.User;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.events.AlertEvent;
import uk.co.thefishlive.maths.events.EventController;
import uk.co.thefishlive.maths.resources.Resource;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.ColorPalette;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.maths.ui.loader.UI;
import uk.co.thefishlive.maths.ui.loader.UILoader;
import uk.co.thefishlive.maths.ui.controllers.admin.UserCreateController.CreateCallback;
import uk.co.thefishlive.maths.ui.loader.icon.IconData;

import static uk.co.thefishlive.maths.Main.getInstance;

public class UserListController extends Controller {

    private static final Logger LOGGER = LogManager.getLogger();

    @FXML private Pane pnlContainer;

    @FXML private GridPane pnlUsers;

    @FXML private Label lblGroupName;

    private GroupProfile group;

    @FXML
    public void itmAddUser_Click(MouseEvent event) throws ResourceException, IOException {
        UI ui = UILoader.loadUI("admin/user_create");

        UserCreateController controller = ui.getController(UserCreateController.class);
        controller.setCreateCallback(profile -> profile.addGroup(group));

        getInstance().setCurrentUI(ui);
    }

    @FXML
    public void itmEdit_Click(MouseEvent event) throws ResourceException, IOException {
        UI ui = UILoader.loadUI("admin/group_edit");

        GroupEditController controller = ui.getController(GroupEditController.class);
        controller.setGroup(this.group);

        getInstance().setCurrentUI(ui);
    }

    @FXML
    public void itmCreateAssignment_Click(MouseEvent event) throws ResourceException, IOException {
        UI ui = UILoader.loadUI("admin/group_create_assignment");

        GroupCreateAssignmentController controller = ui.getController(GroupCreateAssignmentController.class);
        controller.setGroup(getInstance().getAuthHandler().getGroupManager().getGroupProfile(this.group));

        getInstance().setCurrentUI(ui);
    }

    @FXML
    public void itmDelete_Click(MouseEvent event) throws ResourceException, IOException {
        LOGGER.info("Deleting group {}", group.toString());
        showLoadingAnimation();

        getInstance().getAuthHandler().getGroupManager().deleteGroup(group);
        close();

        EventController.getInstance().postEvent(new AlertEvent("Successfully deleted group " + group.getDisplayName()));
        LOGGER.info("Deleted group {} successfully", group.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setGroup(GroupProfile group) {
        this.group = group;
    }

    public void onDisplay() {
        // Update title bar
        this.lblGroupName.setText(this.group.getDisplayName());

        try {
            pnlUsers.getChildren().clear();

            // Get all the users from this group
            List<GroupMemberProfile> users = getInstance().getAuthHandler().getGroupManager().getGroupProfile(group).getUsers();
            int pos = 0;

            for (final GroupMemberProfile user : users) {
                if (pos > 11) {
                    break;
                }

                // Create the panel for this user
                Pane pane = new Pane();
                pane.setEffect(new DropShadow());
                pane.setStyle("-fx-background-color: #FFFFFF;");

                Label text = new Label();
                text.setText(user.getDisplayName());
                text.setLayoutX(47.5f);
                text.setLayoutY(7.5f);
                text.setFont(new Font("Roboto", 16));
                pane.getChildren().add(text);

                Circle circle = new Circle();
                circle.setRadius(17.5f);
                circle.setLayoutX(21.5f);
                circle.setLayoutY(21.5f);
                circle.setFill(ColorPalette.PRIMARY);
                pane.getChildren().add(circle);

                ImageView edit = new ImageView();
                edit.setImage(UILoader.getIconCache().getIcon(new IconData("edit", "grey600", "24dp")).getImage());
                edit.setX(525d);
                edit.setY(8d);
                edit.setFitWidth(24d);
                edit.setFitHeight(24d);
                edit.setCursor(Cursor.HAND);
                edit.setOnMouseClicked(mouseEvent -> {
                    // Open edit screen
                    try {
                        UI ui = UILoader.loadUI("admin/user_edit");

                        ui.getController(UserEditController.class).setUser((UserProfile) user);
                        Main.getInstance().setCurrentUI(ui);
                    } catch (IOException | ResourceException e) {
                        e.printStackTrace();
                    }
                });
                pane.getChildren().add(edit);

                ImageView delete = new ImageView();
                delete.setImage(UILoader.getIconCache().getIcon(new IconData("delete", "grey600", "24dp")).getImage());
                delete.setX(560d);
                delete.setY(8d);
                delete.setFitWidth(24d);
                delete.setFitHeight(24d);
                delete.setCursor(Cursor.HAND);
                delete.setOnMouseClicked(mouseEvent -> {
                    // Delete user
                    try {
                        LOGGER.info("Deleting user {}", user.toString());
                        showLoadingAnimation();

                        getInstance().getAuthHandler().getUserManager().deleteUser((UserProfile) user);
                        onDisplay();

                        EventController.getInstance().postEvent(new AlertEvent("Successfully deleted user " + user.getDisplayName()));

                        hideLoadingAnimation();
                        LOGGER.info("User deleted {} successfully", user.toString());
                    } catch (IOException e) {
                        Throwables.propagate(e);
                    }
                });
                pane.getChildren().add(delete);

                pnlUsers.add(pane, 0, pos++);
            }
        } catch (IOException | ResourceException e) {
            Throwables.propagate(e);
        }
    }

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }
}
