package uk.co.thefishlive.maths.ui.admin;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.google.common.base.Throwables;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import uk.co.thefishlive.auth.group.GroupProfile;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.ui.ColorPalette;
import uk.co.thefishlive.maths.ui.Controller;

public class UserListController implements Controller {

    @FXML private GridPane pnlUsers;
    @FXML private Pane pnlMenu;

    private GroupProfile group;

    @FXML
    public void btnMenu_Clicked(MouseEvent event) {
        System.out.println(pnlMenu);
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), pnlMenu);
        transition.setToX(0);
        transition.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setGroup(GroupProfile group) {
        this.group = group;

        try {
            List<UserProfile> users = Main.getInstance().getAuthHandler().getGroupManager().getGroupProfile(group).getUsers();
            int pos = 0;

            for (UserProfile user : users) {
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

                pnlUsers.add(pane, 0, pos++);
            }
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }
}
