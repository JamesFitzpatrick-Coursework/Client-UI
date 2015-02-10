package uk.co.thefishlive.maths.ui.controllers.assessment;

import com.google.common.base.Throwables;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import uk.co.thefishlive.maths.assessment.Assessment;
import uk.co.thefishlive.maths.assessment.AssessmentView;
import uk.co.thefishlive.maths.assessment.question.Question;
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

    private Assessment assessment;

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }

    @Override
    public void onDisplay() {
        long questions = this.assessment.getQuestions().size();
        long questionsComplete = this.assessment.getQuestions().stream().filter(Question::isAnswered).count();

        lblTitle.setText(this.assessment.getName());
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
    public void btnPrevious_Click(MouseEvent event) {
        try {
            this.assessment.display(AssessmentView.QUESTION);
        } catch (IOException | ResourceException e) {
            Throwables.propagate(e);
        }
    }
}
