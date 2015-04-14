package uk.co.thefishlive.maths.ui.controllers.admin;

import com.google.common.base.Throwables;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uk.co.thefishlive.auth.assessments.AssessmentManager;
import uk.co.thefishlive.auth.assessments.AssessmentProfile;
import uk.co.thefishlive.auth.assessments.assignments.Assignment;
import uk.co.thefishlive.auth.assessments.exception.AssessmentException;
import uk.co.thefishlive.auth.group.Group;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.events.AlertEvent;
import uk.co.thefishlive.maths.events.EventController;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.maths.ui.utils.EffectsUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class GroupCreateAssignmentController extends Controller {

    private static final Logger logger = LogManager.getLogger();

    @FXML private Pane pnlContainer;
    @FXML private Pane pnlForm;

    @FXML private ComboBox<AssessmentProfile> cmbAssessment;
    @FXML private DatePicker dteDeadline;

    @FXML private Label lblErrorAssessment;
    @FXML private Label lblErrorDeadline;

    private Group group;
    private AssessmentManager manager;
    private boolean error = false;

    @FXML
    public void dteDeadline_Action(ActionEvent event) {
        logger.debug(dteDeadline.getValue());

        // If it is a date in the past, error back at the user
        if (dteDeadline.getValue() == null || dteDeadline.getValue().isBefore(LocalDate.now())) {
            lblErrorDeadline.setText("Must pick a deadline in the future");
            lblErrorDeadline.setVisible(true);
            error = true;
        } else {
            // If the date is back valid, hide the error
            lblErrorDeadline.setVisible(false);
            error = false;
        }
    }

    @FXML
    private void btnCreate_Click(ActionEvent event) {
        lblErrorAssessment.setVisible(false);
        lblErrorDeadline.setVisible(false);

        // Check for errors
        if (cmbAssessment.getValue() == null) {
            lblErrorAssessment.setText("Please select an assessment");
            lblErrorAssessment.setVisible(true);
            error = true;
        }

        if (dteDeadline.getValue() == null) {
            lblErrorDeadline.setText("Please enter a deadline");
            lblErrorDeadline.setVisible(true);
            error = true;
        }

        if (error) {
            EffectsUtils.panelShake(pnlForm);
            return;
        }

        // Create the assignment from the given parameters
        Assignment assignment = manager.getAssignmentFactory().createAssignment(cmbAssessment.getValue(), Date.valueOf(dteDeadline.getValue()));
        // Assign this to the group specified
        //this.group.assignAssessment(assignment);

        // Close the current UI
        close();
        EventController.getInstance().postEvent(new AlertEvent("Successfully created assignment for " + cmbAssessment.getValue().getDisplayName()));
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

        dteDeadline.setShowWeekNumbers(false);
        dteDeadline.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #aaaaaa;");
                    return;
                }

                long p = ChronoUnit.DAYS.between(LocalDate.now(), item);
                Tooltip tooltip = new Tooltip(String.format("This assessment is due in %d days", p));

                if (p == 0) {
                    tooltip = new Tooltip("This assessment is due in today");
                    tooltip.setStyle("-fx-text-fill: #FF4F1B;");
                }

                setTooltip(tooltip);
            }
        });
    }

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
