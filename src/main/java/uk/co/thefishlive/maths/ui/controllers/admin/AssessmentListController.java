package uk.co.thefishlive.maths.ui.controllers.admin;

import com.google.common.base.Throwables;

import uk.co.thefishlive.auth.assessments.AssessmentProfile;
import uk.co.thefishlive.auth.assessments.exception.AssessmentException;
import uk.co.thefishlive.auth.group.GroupProfile;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.ColorPalette;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.maths.ui.loader.UI;
import uk.co.thefishlive.maths.ui.loader.UILoader;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 */
public class AssessmentListController extends Controller {

    @FXML private Pane pnlContainer;
    @FXML private Pane pnlMenu;

    @FXML private GridPane pnlAssessments;

    @FXML private Pane pnlAlert;
    @FXML private Label lblAlertMessage;

    @FXML
    private void itmAddAssessment_Click(MouseEvent event) {
        try {
            UI ui = UILoader.loadUI(Main.getInstance().getResourceManager().getResource("ui/admin/assessment_create.fxml"));
            Main.getInstance().setCurrentUI(ui);
        } catch (IOException | ResourceException e) {
            Throwables.propagate(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void onDisplay() {
        try {
            // Remove current assessments
            pnlAssessments.getChildren().clear();

            // Get all assessments
            final List<AssessmentProfile> assessments = Main.getInstance().getAuthHandler().getAssessmentManager().getAssessments();
            int index = 0;

            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 3; x++) {
                    // Build pane for this group
                    final AssessmentProfile assessmentProfile = assessments.get(index);

                    Pane assessment = new Pane();
                    assessment.setEffect(new DropShadow());
                    assessment.setStyle("-fx-background-color: #FFFFFF;");

                    Label text = new Label();
                    text.setText(assessmentProfile.getDisplayName());
                    text.setLayoutX(17.5f);
                    text.setLayoutY(17.5f);
                    text.setFont(new Font("Roboto", 16));
                    assessment.getChildren().add(text);

                    Button button = new Button();
                    button.setLayoutX(117);
                    button.setLayoutY(86);
                    button.setMinSize(60, 25);
                    button.setText("Edit");
                    button.setStyle("-fx-background-color: #2196F3;");
                    button.setCursor(Cursor.HAND);
                    button.setOnMouseClicked(mouseEvent -> {
                        try {
                            showLoadingAnimation();

                            UI ui = UILoader.loadUI(Main.getInstance().getResourceManager().getResource("ui/admin/assessment_question_list.fxml"));
                            ui.setParent(Main.getInstance().getCurrentUI());
                            ui.getController(AssessmentQuestionListController.class).setAssessment(assessmentProfile);

                            Main.getInstance().setCurrentUI(ui);
                        } catch (IOException | ResourceException | AssessmentException e) {
                            e.printStackTrace();
                        } finally {
                            hideLoadingAnimation();
                        }

                    });
                    assessment.getChildren().add(button);

                    // Add this to the UI
                    pnlAssessments.add(assessment, x, y);

                    if (++index >= assessments.size()) {
                        return;
                    }
                }
            }
        } catch (IOException | AssessmentException e) {
            Throwables.propagate(e);
        }
    }

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }
}
