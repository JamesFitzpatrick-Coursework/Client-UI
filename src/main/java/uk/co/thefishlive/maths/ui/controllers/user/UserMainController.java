package uk.co.thefishlive.maths.ui.controllers.user;

import com.google.common.base.Throwables;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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

import uk.co.thefishlive.auth.AuthHandler;
import uk.co.thefishlive.auth.assessments.Assessment;
import uk.co.thefishlive.auth.assessments.assignments.Assignment;
import uk.co.thefishlive.auth.assessments.exception.AssessmentException;
import uk.co.thefishlive.auth.user.User;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.assessment.AssessmentHandler;
import uk.co.thefishlive.maths.assessment.AssessmentView;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.Controller;

public class UserMainController extends Controller {

    @FXML private Pane pnlContainer;

    @FXML private GridPane pnlAssets;
    @FXML private Label lblTitle;

    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Get the current logged in user
        AuthHandler handler = Main.getInstance().getAuthHandler();
        try {
            user = handler.getUserManager().getUserProfile(handler.getActiveSession().getProfile());
        } catch (IOException e) {
            Throwables.propagate(e);
        }

        // Get all the currently outstanding assignments
        List<Assignment> assignments = user.getOutstandingAssignments();

        int i = 0;

        for (Assignment assignment : assignments) {
            try {
                // Build the pane for this assignment
                final Assessment assessment = assignment.getAssessment();

                Pane asset = new Pane();
                asset.setEffect(new DropShadow());
                asset.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                asset.setCursor(Cursor.HAND);
                asset.setOnMouseClicked(event -> {
                    // Load the assignment on click
                    try {
                        AssessmentHandler assessmentHandler = new AssessmentHandler(user, assignment);
                        assessmentHandler.display(AssessmentView.START);
                    } catch (AssessmentException | ResourceException | IOException e) {
                        Throwables.propagate(e);
                    }
                });

                Label label = new Label(assessment.getProfile().getName());
                label.setLayoutX(5);
                label.setLayoutY(5);
                asset.getChildren().add(label);

                // Add this assignment to the screen
                pnlAssets.add(asset, i % 4, (int) Math.floor(i / 4));
                i++;
            } catch (AssessmentException | IOException ex) {
                Throwables.propagate(ex);
            }
        }
    }

    @Override
    public void onDisplay() {
        if (user != null) {
            // Update the title
            this.lblTitle.setText(user.getProfile().getDisplayName());
        }
    }

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }
}
