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
import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.maths.assessment.AssessmentHandler;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.maths.ui.loader.UILoader;
import uk.co.thefishlive.maths.ui.loader.icon.IconData;

import java.io.IOException;

public class SummaryController extends Controller {

    @FXML private Pane pnlContainer;
    @FXML private Pane pnlMenu;

    @FXML private Label lblTitle;
    @FXML private Label lblQuestions;
    @FXML private Label lblQuestionsComplete;

    @FXML private ImageView imgComplete;

    private AssessmentHandler handler;

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }

    @Override
    public void onDisplay() {
        long questions = this.handler.getAssessment().getQuestions().size();
        long questionsComplete = this.handler.getAssessment().getQuestions().stream().filter(Question::isAnswered).count();

        lblTitle.setText(this.handler.getAssessment().getProfile().getDisplayName());
        lblQuestions.setText(String.valueOf(questions));
        lblQuestionsComplete.setText(String.valueOf(questionsComplete));

        if (questionsComplete == questions) {
            try {
                imgComplete.setImage(UILoader.getIconCache().getIcon(new IconData("assignment-complete", "black", "48dp")).getImage());
            } catch (ResourceException e) {
                Throwables.propagate(e);
            }
        }
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
    public void btnPrevious_Click(MouseEvent event) {
    }
}
