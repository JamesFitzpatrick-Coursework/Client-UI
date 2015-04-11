package uk.co.thefishlive.maths.ui.controllers.admin;

import com.google.common.base.Throwables;

import uk.co.thefishlive.auth.assessments.AssessmentManager;
import uk.co.thefishlive.auth.assessments.AssessmentProfile;
import uk.co.thefishlive.auth.assessments.assignments.Assignment;
import uk.co.thefishlive.auth.assessments.exception.AssessmentException;
import uk.co.thefishlive.auth.group.Group;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.ui.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

public class GroupCreateAssignmentController extends Controller {

    @FXML private Pane pnlContainer;

    @FXML private ComboBox<AssessmentProfile> cmbAssessment;
    @FXML private DatePicker dteDeadline;

    @FXML private Label lblErrorAssessment;
    @FXML private Label lblErrorDeadline;

    private Group group;
    private AssessmentManager manager;

    @FXML
    public void dteDeadline_Action(ActionEvent event) {
        // If it is a date in the past, error back at the user
        if (dteDeadline.getValue().isBefore(LocalDate.now())) {
            lblErrorDeadline.setText("Must pick a deadline in the future");
            lblErrorDeadline.setVisible(true);
        } else {
            // If the date is back valid, hide the error
            lblErrorDeadline.setVisible(false);
        }
    }

    @FXML
    private void btnCreate_Click(ActionEvent event) {
        // Create the assignment from the given parameters
        Assignment assignment = manager.getAssignmentFactory().createAssignment(cmbAssessment.getValue(), Date.valueOf(dteDeadline.getValue()));
        // Assign this to the group specified
        this.group.assignAssessment(assignment);

        // Close the current UI
        close();
    }

    @FXML
    private void btnCancel_Click(ActionEvent event) {
        close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.manager = Main.getInstance().getAuthHandler().getAssessmentManager();

        // Setup the combobox handler
        this.cmbAssessment.setConverter(new StringConverter<AssessmentProfile>() {
            @Override
            public String toString(AssessmentProfile object) {
                return object.getDisplayName();
            }

            @Override
            public AssessmentProfile fromString(String string) {
                return null; // FIXME
            }
        });

        try {
            // Add all known assessments to the combo box
            for (AssessmentProfile profile : this.manager.getAssessments()) {
                this.cmbAssessment.getItems().add(profile);
            }
        } catch (IOException | AssessmentException e) {
            Throwables.propagate(e);
        }
    }

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
