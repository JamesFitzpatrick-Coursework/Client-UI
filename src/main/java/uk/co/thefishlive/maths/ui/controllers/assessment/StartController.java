package uk.co.thefishlive.maths.ui.controllers.assessment;

import com.google.common.base.Throwables;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import uk.co.thefishlive.maths.assessment.Assessment;
import uk.co.thefishlive.maths.assessment.AssessmentView;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 */
public class StartController extends Controller {

    @FXML private Pane pnlContainer;
    @FXML private Pane pnlMenu;

    @FXML private Label lblTitle;
    @FXML private Label lblQuestions;

    private Assessment assessment;

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }

    @Override
    public void onDisplay() {
        lblTitle.setText(this.assessment.getName());
        lblQuestions.setText(String.valueOf(this.assessment.getQuestions().size()));
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    @FXML
    public void btnMenu_Click(MouseEvent event) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), pnlMenu);
        transition.setByX(205);
        transition.play();
    }

    @FXML
    public void btnNext_Click(MouseEvent event) {
        try {
            this.assessment.display(AssessmentView.QUESTION);
        } catch (IOException | ResourceException e) {
            Throwables.propagate(e);
        }
    }
}
