package uk.co.thefishlive.maths.ui.controllers.user;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.maths.ui.utils.EffectsUtils;

/**
 *
 */
public class UserMainController extends Controller {

    @FXML private Pane pnlContainer;

    @FXML private GridPane pnlAssets;
    @FXML private Pane pnlMenu;

    @FXML private Label lblTitle;

    @FXML
    public void btnMenu_Clicked(MouseEvent event) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), pnlMenu);
        transition.setByX(205);
        transition.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 2; y++) {
                Pane asset = new Pane();
                asset.setEffect(new DropShadow());
                asset.setStyle("-fx-background-color: #FFFFFF;");
                pnlAssets.add(asset, x, y);
            }
        }
    }

    @Override
    public void onDisplay() {
        this.lblTitle.setText(Main.getInstance().getAuthHandler().getActiveSession().getProfile().getDisplayName());
    }

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }
}
