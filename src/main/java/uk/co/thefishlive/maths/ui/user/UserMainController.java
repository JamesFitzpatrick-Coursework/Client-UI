package uk.co.thefishlive.maths.ui.user;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.ui.utils.EffectsUtils;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 */
public class UserMainController implements Initializable {

    @FXML private Pane pnlContainer;

    @FXML private GridPane pnlAssets;
    @FXML private Pane pnlMenu;

    @FXML private Pane pnlAlert;
    @FXML private Label lblAlertMessage;

    @FXML
    public void btnMenu_Clicked(MouseEvent event) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(1000), pnlMenu);
        transition.setByX(205);
        transition.play();
    }

    @FXML
    public void btnMenuClose_Clicked(MouseEvent event) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(1000), pnlMenu);
        transition.setByX(-205);
        transition.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pnlAlert.setVisible(true);
        lblAlertMessage.setText("Successfully logged in as " + Main.getInstance().getCurrentSession().getOwner().getDisplayName());

        EffectsUtils.fadeIn(Duration.seconds(1), pnlAlert).play();
        EffectsUtils.fadeOut(Duration.seconds(3), pnlAlert, Duration.seconds(5)).play();

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 4; y++) {
                Pane asset = new Pane();
                asset.setEffect(new DropShadow());
                asset.setStyle("-fx-background-color: #FFFFFF;");
                pnlAssets.add(asset, x, y);
            }
        }
    }
}