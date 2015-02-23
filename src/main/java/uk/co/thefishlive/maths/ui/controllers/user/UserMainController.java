package uk.co.thefishlive.maths.ui.controllers.user;

import com.google.common.base.Throwables;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import uk.co.thefishlive.auth.assessments.Assessment;
import uk.co.thefishlive.auth.assessments.AssessmentBuilder;
import uk.co.thefishlive.auth.assessments.questions.QuestionType;
import uk.co.thefishlive.auth.assessments.questions.multichoice.MultichoiceQuestionBuilder;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.assessment.AssessmentHandler;
import uk.co.thefishlive.maths.assessment.AssessmentView;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.auth.assessments.exception.AssessmentCreateException;

/**
 *
 */
public class UserMainController extends Controller {

    @FXML
    private Pane pnlContainer;

    @FXML
    private GridPane pnlAssets;
    @FXML
    private Pane pnlMenu;

    @FXML
    private Label lblTitle;

    @FXML
    public void btnMenu_Clicked(MouseEvent event) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), pnlMenu);
        transition.setByX(205);
        transition.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Pane asset = new Pane();
        asset.setEffect(new DropShadow());
        asset.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        asset.setCursor(Cursor.HAND);
        asset.setOnMouseClicked(event -> {
            try {
                AssessmentBuilder builder = Main.getInstance().getAuthHandler().getAssessmentFactory().createAssessmentBuilder();

                MultichoiceQuestionBuilder questionBuilder = (MultichoiceQuestionBuilder) builder.createQuestionBuilder(QuestionType.MULTI_CHOICE);
                questionBuilder.setQuestionNumber(1);
                questionBuilder.setQuestion("A test question");
                questionBuilder.addOption("Answer 1");
                questionBuilder.addOption("Answer 2");
                questionBuilder.addOption("Answer 3");
                questionBuilder.addOption("Answer 4");
                builder.addQuestion(questionBuilder.build());

                MultichoiceQuestionBuilder questionBuilder2 = (MultichoiceQuestionBuilder) builder.createQuestionBuilder(QuestionType.MULTI_CHOICE);
                questionBuilder2.setQuestionNumber(2);
                questionBuilder2.setQuestion("A test question 2");
                questionBuilder2.addOption("Answer 1");
                questionBuilder2.addOption("Answer 2");
                questionBuilder2.addOption("Answer 3");
                questionBuilder2.addOption("Answer 4");
                builder.addQuestion(questionBuilder2.build());

                Assessment assessment = builder.build();
                AssessmentHandler handler = new AssessmentHandler(assessment);
                handler.display(AssessmentView.START);
            } catch (AssessmentCreateException | ResourceException | IOException e) {
                e.printStackTrace();
                Throwables.propagate(e);
            }
        });
        pnlAssets.add(asset, 0, 0);
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
