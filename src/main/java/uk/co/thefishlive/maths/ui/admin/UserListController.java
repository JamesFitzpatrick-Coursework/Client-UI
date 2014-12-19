package uk.co.thefishlive.maths.ui.admin;

import java.net.URL;
import java.util.ResourceBundle;
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
import uk.co.thefishlive.maths.ui.ColorPalette;

public class UserListController implements Initializable {

    @FXML private GridPane pnlUsers;
    @FXML private Pane pnlMenu;

    @FXML
    public void btnMenu_Clicked(MouseEvent event) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), pnlMenu);
        transition.setByX(205);
        transition.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < 11; i++) {
            Pane pane = new Pane();
            pane.setEffect(new DropShadow());
            pane.setStyle("-fx-background-color: #FFFFFF;");

            Label text = new Label();
            text.setText("Student " + i);
            text.setLayoutX(47.5);
            text.setLayoutY(7.5f);
            text.setFont(new Font("Roboto", 16));
            pane.getChildren().add(text);

            Circle circle = new Circle();
            circle.setRadius(17.5);
            circle.setLayoutX(21.5);
            circle.setLayoutY(21.5);
            circle.setFill(ColorPalette.PRIMARY);
            pane.getChildren().add(circle);

            pnlUsers.add(pane, 0, i);
        }
    }
}
