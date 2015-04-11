package uk.co.thefishlive.maths.ui.controllers.assessment;

import com.google.common.base.Throwables;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import uk.co.thefishlive.maths.assessment.AssessmentHandler;
import uk.co.thefishlive.maths.assessment.AssessmentView;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.Controller;

import java.io.IOException;

/**
 *
 */
public class StartController extends Controller {

    @FXML private Pane pnlContainer;

    @FXML private Label lblTitle;
    @FXML private Label lblQuestions;

    private AssessmentHandler handler;

    @FXML
    public void btnNext_Click(MouseEvent event) {
        try {
            handler.display(AssessmentView.QUESTION);
        } catch (IOException | ResourceException e) {
            Throwables.propagate(e);
        }
    }

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

}
