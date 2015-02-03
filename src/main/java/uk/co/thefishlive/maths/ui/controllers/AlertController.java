package uk.co.thefishlive.maths.ui.controllers;

import com.google.common.eventbus.Subscribe;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import uk.co.thefishlive.maths.events.AlertEvent;
import uk.co.thefishlive.maths.events.EventController;
import uk.co.thefishlive.maths.events.Listener;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.maths.ui.utils.EffectsUtils;

public class AlertController extends Controller implements Listener {

    @FXML private Pane pnlAlert;
    @FXML private Label lblAlertMessage;

    public AlertController() {
        EventController.getInstance().registerHandler(this);
    }

    @Override
    protected Pane getContentPane() {
        return pnlAlert;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pnlAlert.setVisible(false);
    }

    @Subscribe
    public void onAlert(AlertEvent event) {
        pnlAlert.setVisible(true);

        lblAlertMessage.setText(event.getMessage());

        EffectsUtils.fadeIn(Duration.seconds(1), pnlAlert).play();
        EffectsUtils.fadeOut(Duration.seconds(1), pnlAlert, Duration.seconds(5)).play();
    }
}
