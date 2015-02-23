package uk.co.thefishlive.maths.ui.controllers.assessment;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import uk.co.thefishlive.maths.assessment.AssessmentHandler;
import uk.co.thefishlive.maths.ui.Controller;

/**
 *
 */
public class StartController extends Controller {

    @FXML private Pane pnlContainer;
    @FXML private Pane pnlMenu;

    @FXML private Label lblTitle;
    @FXML private Label lblQuestions;

    private AssessmentHandler handler;

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }

    @Override
    public void onDisplay() {
        lblTitle.setText(this.handler.getAssessment().getProfile().getDisplayName());
        lblQuestions.setText(String.valueOf(this.handler.getAssessment().getQuestions().size()));
    }

    public void setHandler(AssessmentHandler handler) {
        this.handler = handler;
    }

    @FXML
    public void btnMenu_Click(MouseEvent event) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), pnlMenu);
        transition.setByX(205);
        transition.play();
    }

    @FXML
    public void btnNext_Click(MouseEvent event) {
    }
}
