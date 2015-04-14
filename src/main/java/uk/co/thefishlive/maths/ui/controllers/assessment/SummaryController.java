package uk.co.thefishlive.maths.ui.controllers.assessment;

import com.google.common.base.Throwables;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import uk.co.thefishlive.auth.assessments.assignments.AssignmentResult;
import uk.co.thefishlive.auth.assessments.assignments.QuestionScore;
import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.assessment.AssessmentHandler;
import uk.co.thefishlive.maths.assessment.AssessmentView;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.maths.ui.loader.UI;
import uk.co.thefishlive.maths.ui.loader.UILoader;
import uk.co.thefishlive.maths.ui.loader.icon.IconData;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class SummaryController extends Controller {

    private static final Logger logger = LogManager.getLogger();

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

    @SuppressWarnings("deprecation")
    @FXML
    public void btnSubmit_Click(MouseEvent event) {
        if (!complete) {
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Submitting assessment");
        alert.setContentText("Are you sure you have completed the assessment?");
        Optional<ButtonType> alertResult = alert.showAndWait();

        if (alertResult.isPresent() && alertResult.get() == ButtonType.OK) {
            AssignmentResult result = handler.getTarget().submitAssessment(handler.getAssignment(), handler.getAssessment());
            showDialog(result);

            System.out.println(result.getScores());

            try {
                UI ui = UILoader.loadUI("user_main");
                Main.getInstance().setCurrentUI(ui);
            } catch (IOException | ResourceException e) {
                Throwables.propagate(e);
            }
        }

    }

    @SuppressWarnings("unchecked")
    private void showDialog(AssignmentResult result) {
        // Create the dialog
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Scores");
        alert.setHeaderText("");
        alert.setContentText("You have scored " + result.getTotalScore() + " out of " + result.getMaximumScore() + " (" + Math.round(result.getPercentage()) + "%)");

        ObservableList<QuestionScore> scores = FXCollections.observableArrayList(result.getScores().values());
        TableView<QuestionScore> table = new TableView<>();

        // Create a auto numbering question column
        TableColumn<QuestionScore, Number> questionNumber = new TableColumn("Question");
        questionNumber.setCellValueFactory(new PropertyValueFactory<>("questionNumber"));
        questionNumber.setSortable(false);
        questionNumber.setResizable(false);
        questionNumber.setMinWidth(100);

        // Create a score column
        TableColumn<QuestionScore, Number> score = new TableColumn("Score");
        score.setCellFactory(param -> new TableCell<QuestionScore, Number>(){
            public void updateItem(Number score, boolean empty) {
                if(score != null){
                    try {
                        HBox box = new HBox();
                        box.setSpacing(10);
                        VBox vbox = new VBox();

                        // Create a
                        ImageView imageview = new ImageView();
                        imageview.setLayoutX(38);
                        imageview.setFitHeight(24);
                        imageview.setFitWidth(24);
                        imageview.setImage(UILoader.getIconCache().getIcon(new IconData(score.intValue() == 1 ? "check" : "close", "black", "24dp")).getImage());

                        box.getChildren().addAll(imageview, vbox);
                        //SETTING ALL THE GRAPHICS COMPONENT FOR CELL
                        setGraphic(box);
                    } catch (ResourceException ex) {
                        Throwables.propagate(ex);
                    }
                }
            }
        });
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        score.setSortable(false);
        score.setResizable(false);
        score.setMinWidth(100);

        // Add the columns to the table
        table.getColumns().addAll(questionNumber, score);
        table.setItems(scores);

        // Show the dialog box
        alert.getDialogPane().setExpandableContent(table);
        alert.showAndWait();
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
