package uk.co.thefishlive.maths.ui.controllers.assessment;

import com.google.common.base.Throwables;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import uk.co.thefishlive.auth.assessments.Assessment;
import uk.co.thefishlive.auth.assessments.assignments.AssignmentResult;
import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.maths.assessment.AssessmentHandler;
import uk.co.thefishlive.maths.assessment.AssessmentView;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.maths.ui.loader.UILoader;
import uk.co.thefishlive.maths.ui.loader.icon.IconData;

import java.io.IOException;

public class SummaryController extends Controller {

    @FXML private Pane pnlContainer;

    @FXML private Label lblTitle;
    @FXML private Label lblQuestions;
    @FXML private Label lblQuestionsComplete;

    @FXML private ImageView imgComplete;

    @FXML private ImageView btnSubmit;

    private AssessmentHandler handler;
    private boolean complete;

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }

    @FXML
    public void btnPrevious_Click(MouseEvent event) {
        try {
            handler.display(AssessmentView.QUESTION);
        } catch (IOException | ResourceException e) {
            Throwables.propagate(e);
        }
    }

    @FXML
    public void btnSubmit_Click(MouseEvent event) {
        if (!complete) {
            return;
        }

        AssignmentResult result = handler.getTarget().submitAssessment(handler.getAssignment(), handler.getAssessment());
        System.out.println(result.getScores());
    }

    @Override
    public void onDisplay() {
        long questions = this.handler.getAssessment().getQuestions().size();
        long questionsComplete = this.handler.getAssessment().getQuestions().stream().filter(Question::isAnswered).count();

        lblTitle.setText(this.handler.getAssessment().getProfile().getDisplayName());
        lblQuestions.setText(String.valueOf(questions));
        lblQuestionsComplete.setText(String.valueOf(questionsComplete));

        if (questionsComplete == questions) {
            btnSubmit.setVisible(true);
            complete = true;

            try {
                imgComplete.setImage(UILoader.getIconCache().getIcon(new IconData("assessment-complete", "black", "48dp")).getImage());
            } catch (ResourceException e) {
                Throwables.propagate(e);
            }
        } else {
            btnSubmit.setVisible(false);
            complete = false;
        }
    }

    public void setHandler(AssessmentHandler handler) {
        this.handler = handler;
    }
}
