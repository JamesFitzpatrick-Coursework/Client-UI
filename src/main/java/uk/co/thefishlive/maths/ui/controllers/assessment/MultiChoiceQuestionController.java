package uk.co.thefishlive.maths.ui.controllers.assessment;

import com.google.common.base.Throwables;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import uk.co.thefishlive.maths.assessment.Assessment;
import uk.co.thefishlive.maths.assessment.AssessmentView;
import uk.co.thefishlive.maths.assessment.exceptions.EndOfAssessmentException;
import uk.co.thefishlive.maths.assessment.question.multichoice.MultiChoiceQuestion;
import uk.co.thefishlive.maths.assessment.question.multichoice.Option;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.Controller;

public class MultiChoiceQuestionController extends Controller {

    private MultiChoiceQuestion question;
    private Assessment assessment;
    private Pane clicked;

    @FXML private Pane pnlContainer;
    @FXML private Pane pnlMenu;

    @FXML private GridPane pnlOptions;

    @FXML private Label lblQuestion;
    @FXML private Label lblQuestionNumber;
    @FXML private Label lblTitle;
    private Option clickedOption;

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void onDisplay() {
        this.lblTitle.setText(this.assessment.getName());
        this.lblQuestion.setText(this.question.getQuestion());
        this.lblQuestionNumber.setText(this.question.getQuestionNumber() + ".");

        for (int i = 0; i < this.question.getOptions().size(); i++) {
            Pane pane = new Pane();
            pane.setId("option-" + i);
            pane.setEffect(new DropShadow());
            pane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
            pane.setCursor(Cursor.HAND);
            pane.setOnMouseClicked(event -> {
                if (this.clicked != null) {
                    this.clicked.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                }

                pane.setBackground(new Background(new BackgroundFill(Color.web("#C8E6C9"), null, null)));
                this.clicked = pane;
            });

            if (this.question.getAnswer() == i) {
                this.clicked = pane;
                pane.setBackground(new Background(new BackgroundFill(Color.web("#C8E6C9"), null, null)));
            }

            Label label = new Label(this.question.getOptions().get(i).getText());
            label.setLayoutX(5d);
            label.setLayoutY(5d);
            label.setWrapText(true);
            label.setMaxWidth(170);
            pane.getChildren().add(label);

            this.pnlOptions.add(pane, i, 0);
        }
    }

    public void setQuestion(Assessment assessment) {
        this.assessment = assessment;
        this.question = assessment.getCurrentQuestion(MultiChoiceQuestion.class);
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
            try {
                this.assessment.getCurrentQuestion(MultiChoiceQuestion.class).setAnswer(getClickedOption());

                this.assessment.nextQuestion();
            } catch (EndOfAssessmentException e) {
                // End assessment
                this.assessment.display(AssessmentView.SUMMARY);
            }
        } catch (IOException | ResourceException e) {
            Throwables.propagate(e);
        }
    }

    @FXML
    public void btnPrevious_Click(MouseEvent event) {
        try {
            try {
                this.assessment.getCurrentQuestion(MultiChoiceQuestion.class).setAnswer(getClickedOption());

                this.assessment.previousQuestion();
            } catch (EndOfAssessmentException e) {
                this.assessment.display(AssessmentView.START);
            }
        } catch (IOException | ResourceException e) {
            Throwables.propagate(e);
        }
    }

    public int getClickedOption() {
        if (this.clicked == null) {
            return MultiChoiceQuestion.NOT_ANSWERED;
        }

        String id = this.clicked.getId();
        return Integer.parseInt(id.substring(id.lastIndexOf('-') + 1));
    }
}
