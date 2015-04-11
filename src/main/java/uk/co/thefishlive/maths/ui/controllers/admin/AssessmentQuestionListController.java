package uk.co.thefishlive.maths.ui.controllers.admin;

import com.google.common.base.Throwables;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uk.co.thefishlive.auth.assessments.Assessment;
import uk.co.thefishlive.auth.assessments.AssessmentFactory;
import uk.co.thefishlive.auth.assessments.AssessmentManager;
import uk.co.thefishlive.auth.assessments.AssessmentProfile;
import uk.co.thefishlive.auth.assessments.exception.AssessmentException;
import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.auth.assessments.questions.QuestionBuilder;
import uk.co.thefishlive.auth.assessments.questions.QuestionType;
import uk.co.thefishlive.auth.assessments.questions.multichoice.MultichoiceQuestionBuilder;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.events.AlertEvent;
import uk.co.thefishlive.maths.events.EventController;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.maths.ui.controllers.editor.MultichoiceQuestionEditorController;
import uk.co.thefishlive.maths.ui.controllers.editor.QuestionEditorController;
import uk.co.thefishlive.maths.ui.loader.UI;
import uk.co.thefishlive.maths.ui.loader.UILoader;
import uk.co.thefishlive.maths.ui.loader.icon.IconData;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;

import static uk.co.thefishlive.auth.assessments.questions.QuestionType.*;
import static uk.co.thefishlive.maths.Main.getInstance;

public class AssessmentQuestionListController extends Controller {

    private static final Logger LOGGER = LogManager.getLogger();

    @FXML private Pane pnlMenu;
    @FXML private Pane pnlContainer;

    @FXML private GridPane pnlQuestions;

    @FXML private Label lblGroupName;

    private Assessment assessment;
    private AssessmentManager manager;
    private AssessmentProfile profile;

    @FXML
    public void itmAddMutlichoiceQuestion_Click(MouseEvent event) throws ResourceException, IOException {
        UI ui = UILoader.loadUI("editor/editor_multichoice_question");

        MultichoiceQuestionEditorController controller = ui.getController(MultichoiceQuestionEditorController.class);
        controller.setEditorInfo(manager.getAssessmentFactory().createQuestionBuilder(MULTI_CHOICE), assessment.getQuestions().size() + 1);
        controller.setCallback(assessment::addQuestion);

        Main.getInstance().setCurrentUI(ui);
    }

    @FXML
    public void itmAddAnswerQuestion_Click(MouseEvent event) throws ResourceException, IOException {
    }

    @FXML
    public void itmDeleteAssessment_Click(MouseEvent event) throws AssessmentException, IOException, ResourceException {
        LOGGER.info("Deleting assessment {}", assessment.toString());
        showLoadingAnimation();

        getInstance().getAuthHandler().getAssessmentManager().deleteAssessment(assessment);

        UI ui = UILoader.loadUI("ui/admin/assessment_list");
        Main.getInstance().setCurrentUI(ui);
        LOGGER.info("Deleted assessment {} successfully", assessment.toString());
        EventController.getInstance().postEvent(new AlertEvent("Deleted assessment " + assessment.getProfile().getName() +  " successfully"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = Main.getInstance().getAuthHandler().getAssessmentManager();
    }

    public void setAssessment(AssessmentProfile assessment) throws IOException, AssessmentException {
        this.profile = assessment;
    }

    @Override
    public void onDisplay() {
        final AssessmentFactory assessmentFactory = manager.getAssessmentFactory();

        try {
            this.assessment = Main.getInstance().getAuthHandler().getAssessmentManager().getAssessment(profile);
            pnlQuestions.getChildren().clear();

            // Update title bar
            this.lblGroupName.setText(this.assessment.getProfile().getDisplayName());

            // Get all the questions from this assessment
            List<Question> questions = assessment.getQuestions();
            int pos = 0;

            for (final Question question : questions) {
                if (pos > 11) {
                    break;
                }

                // Create the panel for this question
                Pane pane = new Pane();
                pane.setEffect(new DropShadow());
                pane.setStyle("-fx-background-color: #FFFFFF;");

                Label text = new Label();
                text.setText(question.getQuestionNumber() + ". " + question.getType().name());
                text.setLayoutX(7.5f);
                text.setLayoutY(7.5f);
                text.setFont(new Font("Roboto", 16));
                pane.getChildren().add(text);

                ImageView edit = new ImageView();
                edit.setImage(UILoader.getIconCache().getIcon(new IconData("edit", "grey600", "24dp")).getImage());
                edit.setX(486d);
                edit.setY(8d);
                edit.setFitWidth(24d);
                edit.setFitHeight(24d);
                edit.setCursor(Cursor.HAND);
                edit.setOnMouseClicked(mouseEvent -> {
                    // Open edit screen
                    try {
                        UI ui = null;


                        if (question.getType() == QuestionType.MULTI_CHOICE) {
                            ui = UILoader.loadUI(getInstance().getResourceManager().getResource("ui/editor/editor_multichoice_question.fxml"));
                        } else if (question.getType() == QuestionType.ANSWER) {
                            ui = UILoader.loadUI(getInstance().getResourceManager().getResource("ui/editor/editor_answer_question.fxml"));
                        }

                        QuestionBuilder builder = assessmentFactory.createQuestionBuilder(question);
                        QuestionEditorController controller = ui.getController(QuestionEditorController.class);
                        controller.setCallback(callback -> assessment.updateQuestion(callback.getId(), callback));
                        controller.setEditorInfo(builder, builder.getQuestionNumber());

                        Main.getInstance().setCurrentUI(ui);
                    } catch (IOException | ResourceException e) {
                        Throwables.propagate(e);
                    }
                });
                pane.getChildren().add(edit);

                ImageView delete = new ImageView();
                delete.setImage(UILoader.getIconCache().getIcon(new IconData("delete", "grey600", "24dp")).getImage());
                delete.setX(518d);
                delete.setY(8d);
                delete.setFitWidth(24d);
                delete.setFitHeight(24d);
                delete.setCursor(Cursor.HAND);
                delete.setOnMouseClicked(mouseEvent -> {
                    // Delete user
                    try {
                        LOGGER.info("Deleting question {}", question.toString());
                        showLoadingAnimation();

                        assessment.deleteQuestion(question.getId());
                        onDisplay();

                        hideLoadingAnimation();
                        EventController.getInstance().postEvent(new AlertEvent("Question number " + question.getQuestionNumber() + " deleted successfully"));
                        LOGGER.info("Question deleted {} successfully", question.getId());
                    } catch (IOException | AssessmentException e) {
                        Throwables.propagate(e);
                    }
                });
                pane.getChildren().add(delete);

                pnlQuestions.add(pane, 0, pos++);
            }
        } catch (ResourceException | IOException | AssessmentException e) {
            Throwables.propagate(e);
        }
    }

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }
}
