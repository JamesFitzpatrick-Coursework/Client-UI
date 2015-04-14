package uk.co.thefishlive.maths.ui.controllers.admin;

import com.google.common.base.Throwables;

import uk.co.thefishlive.auth.assessments.Assessment;
import uk.co.thefishlive.auth.assessments.AssessmentBuilder;
import uk.co.thefishlive.auth.assessments.AssessmentManager;
import uk.co.thefishlive.auth.assessments.exception.AssessmentCreateException;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.events.AlertEvent;
import uk.co.thefishlive.maths.events.EventController;
import uk.co.thefishlive.maths.tasks.TaskManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class AssessmentCreateController extends CreateController<Assessment> {

    @FXML private TextField txtName;
    @FXML private TextField txtDisplayname;

    @FXML private Label lblErrorName;
    @FXML private Label lblErrorDisplayname;

    @FXML private Pane pnlMenu;
    @FXML private Pane pnlContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void btnCreate_Click(ActionEvent event) {
        showLoadingAnimation();

        boolean error = false;

        // Validate input
        if (txtName.getText().length() <= 0) {
            lblErrorName.setVisible(true);
            error = true;
        }
        if (txtDisplayname.getText().length() <= 0) {
            lblErrorDisplayname.setVisible(true);
            error = true;
        }

        if (error) {
            // Something went wrong exit now
            hideLoadingAnimation();
            return;
        }

        // Create new user
        AssessmentManager manager = Main.getInstance().getAuthHandler().getAssessmentManager();
        AssessmentBuilder builder = manager.getAssessmentFactory().createAssessmentBuilder();

        builder.setName(txtName.getText());

        // Create the group off the main thread
        TaskManager.runTaskAsync("Create Group", () -> {
            try {
                // Send the creation request
                Assessment assessment = builder.build();

                TaskManager.runTaskSync("Create Group Callback", () -> {
                    if (callback != null) callback.created(assessment);

                    // Display the success back to the user
                    EventController.getInstance().postEvent(new AlertEvent("Created assessment " + assessment.getProfile().getDisplayName()));
                    Main.getInstance().setCurrentUI(getParent());
                });
            } catch (IOException | AssessmentCreateException e) {
                // ERROR
                Throwables.propagate(e);
            } finally {
                hideLoadingAnimation();
            }
        });
    }

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }

}
