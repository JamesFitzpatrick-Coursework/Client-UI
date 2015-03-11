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

    @FXML
    private Pane pnlContainer;
    @FXML
    private GridPane pnlAssets;
    @FXML
    private Pane pnlMenu;
    @FXML
    private Label lblTitle;

    private User user;

    @FXML
    public void btnMenu_Clicked(MouseEvent event) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), pnlMenu);
        transition.setByX(205);
        transition.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AuthHandler handler = Main.getInstance().getAuthHandler();
        try {
            user = handler.getUserManager().getUserProfile(handler.getActiveSession().getProfile());
        } catch (IOException e) {
            Throwables.propagate(e);
        }

        List<Assignment> assignments = user.getOutstandingAssignments();

        int i = 0;

        for (Assignment assignment : assignments) {
            try {
                System.out.println(assignment);
                final Assessment assessment = assignment.getAssessment();
                System.out.println(assessment);

                Pane asset = new Pane();
                asset.setEffect(new DropShadow());
                asset.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                asset.setCursor(Cursor.HAND);
                asset.setOnMouseClicked(event -> {
                    try {
                        AssessmentHandler assessmentHandler = new AssessmentHandler(assessment);
                        assessmentHandler.display(AssessmentView.START);
                    } catch (ResourceException | IOException e) {
                        Throwables.propagate(e);
                    }
                });

                Label label = new Label(assessment.getProfile().getName());
                label.setLayoutX(5);
                label.setLayoutY(5);
                asset.getChildren().add(label);

                pnlAssets.add(asset, i % 4, (int) Math.floor(i / 4));
            } catch (AssessmentException | IOException ex) {
                Throwables.propagate(ex);
            }
        }
    }

    @Override
    public void onDisplay() {
        if (user != null) {
            this.lblTitle.setText(user.getProfile().getDisplayName());
        }
    }

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }
}
