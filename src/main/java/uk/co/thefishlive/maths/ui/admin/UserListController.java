package uk.co.thefishlive.maths.ui.admin;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.google.common.base.Throwables;
import com.sun.xml.internal.bind.v2.TODO;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
import uk.co.thefishlive.auth.user.User;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.ColorPalette;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.maths.ui.UI;
import uk.co.thefishlive.maths.ui.UILoader;
import uk.co.thefishlive.maths.ui.admin.UserCreateController.CreateCallback;
import uk.co.thefishlive.meteor.user.MeteorUserProfile;

import javax.imageio.ImageIO;

import static uk.co.thefishlive.maths.Main.getInstance;

public class UserListController extends Controller {

    private static final Logger LOGGER = LogManager.getLogger();

    private static Image EDIT_ICON;
    private static Image DELETE_ICON;

    static {
        try {
            EDIT_ICON = new Image(getInstance().getResourceManager().getResource("images/icons/ic_mode_edit_black_36dp.png").getContent());
            DELETE_ICON = new Image(getInstance().getResourceManager().getResource("images/icons/ic_delete_black_36dp.png").getContent());
        } catch (ResourceException e) {
            Throwables.propagate(e);
        }
    }

    @FXML private GridPane pnlUsers;
    @FXML private Pane pnlMenu;
    @FXML private Pane pnlContainer;

    @FXML private Label lblGroupName;

    private GroupProfile group;

    @FXML
    public void btnMenu_Click(MouseEvent event) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), pnlMenu);
        transition.setByX(205);
        transition.play();
    }

    @FXML
    public void itmAddUser_Click(MouseEvent event) throws ResourceException, IOException {
        UI ui = UILoader.loadUI(getInstance().getResourceManager().getResource("ui/admin/user_create.fxml"));

        UserCreateController controller = ui.getController(UserCreateController.class);
        controller.setParent(getInstance().getCurrentUI());
        controller.setCreateCallback(new CreateCallback() {
            @Override
            public void userCreated(User profile) {
                profile.addGroup(group);
            }
        });

        getInstance().setCurrentUI(ui);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setGroup(GroupProfile group) {
        this.group = group;
        this.lblGroupName.setText(this.group.getDisplayName());
    }

    public void onDisplay() {
        try {
            pnlUsers.getChildren().clear();
            
            List<UserProfile> users = getInstance().getAuthHandler().getGroupManager().getGroupProfile(group).getUsers();
            int pos = 0;

            for (final UserProfile user : users) {
                if (pos > 11) {
                    break;
                }

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
                edit.setImage(EDIT_ICON);
                edit.setX(525d);
                edit.setY(4d);
                edit.setFitWidth(32d);
                edit.setFitHeight(32d);
                pane.getChildren().add(edit);

                ImageView delete = new ImageView();
                edit.setImage(DELETE_ICON);
                edit.setX(560d);
                edit.setY(4d);
                edit.setFitWidth(32d);
                edit.setFitHeight(32d);
                edit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        try {
                            LOGGER.info("Deleting user {}", user.toString());
                            showLoadingAnimation(pnlContainer);

                            getInstance().getAuthHandler().getUserManager().deleteUser(user);
                            onDisplay();

                            hideLoadingAnimation(pnlContainer);
                            LOGGER.info("User deleted {} successfully", user.toString());
                        } catch (ResourceException | IOException e) {
                            Throwables.propagate(e);
                        }
                    }
                });
                pane.getChildren().add(delete);

                pnlUsers.add(pane, 0, pos++);
            }
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }
}
