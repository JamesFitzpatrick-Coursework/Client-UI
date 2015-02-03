package uk.co.thefishlive.maths.ui.controllers.admin;

import com.google.common.base.Throwables;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import uk.co.thefishlive.auth.group.Group;
import uk.co.thefishlive.auth.group.GroupProfile;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.ColorPalette;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.maths.ui.loader.UI;
import uk.co.thefishlive.maths.ui.loader.UILoader;
import uk.co.thefishlive.maths.ui.utils.EffectsUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 */
public class GroupListController extends Controller {

    @FXML private Pane pnlContainer;
    @FXML private Pane pnlMenu;

    @FXML private GridPane pnlGroups;

    @FXML private Pane pnlAlert;
    @FXML private Label lblAlertMessage;

    @FXML
    public void btnMenu_Click(MouseEvent event) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), pnlMenu);
        transition.setByX(205);
        transition.play();
    }

    @FXML
    private void itmAddGroup_Click(MouseEvent event) {
        try {
            UI ui = UILoader.loadUI(Main.getInstance().getResourceManager().getResource("ui/admin/group_create.fxml"));

            ui.getController(GroupCreateController.class).setCreateCallback(new GroupCreateController.CreateCallback() {
                @Override
                public void groupCreated(Group profile) {
                }
            });

            Main.getInstance().setCurrentUI(ui);
        } catch (IOException | ResourceException e) {
            Throwables.propagate(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void onDisplay() {
        try {
            pnlGroups.getChildren().clear();

            final List<GroupProfile> groups = Main.getInstance().getAuthHandler().getGroupManager().getGroups();
            int index = 0;

            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 3; x++) {
                    final GroupProfile groupProfile = groups.get(index);

                    Pane group = new Pane();
                    group.setEffect(new DropShadow());
                    group.setStyle("-fx-background-color: #FFFFFF;");

                    Label text = new Label();
                    text.setText(groupProfile.getDisplayName());
                    text.setLayoutX(57.5f);
                    text.setLayoutY(17.5f);
                    text.setFont(new Font("Roboto", 16));
                    group.getChildren().add(text);

                    Circle circle = new Circle();
                    circle.setRadius(17.5f);
                    circle.setLayoutX(31.5f);
                    circle.setLayoutY(31.5f);
                    circle.setFill(ColorPalette.PRIMARY);
                    group.getChildren().add(circle);

                    Button button = new Button();
                    button.setLayoutX(117);
                    button.setLayoutY(86);
                    button.setMinSize(60, 25);
                    button.setText("Edit");
                    button.setStyle("-fx-background-color: #2196F3;");
                    button.setCursor(Cursor.HAND);
                    button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            try {
                                showLoadingAnimation();

                                UI ui = UILoader.loadUI(Main.getInstance().getResourceManager().getResource("ui/admin/user_list.fxml"));
                                ui.setParent(Main.getInstance().getCurrentUI());
                                UserListController controller = (UserListController) ui.getController();
                                controller.setGroup(groupProfile);
                                Main.getInstance().setCurrentUI(ui);
                            } catch (IOException | ResourceException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    group.getChildren().add(button);

                    pnlGroups.add(group, x, y);

                    if (++index >= groups.size()) {
                        return;
                    }
                }
            }
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }
}
