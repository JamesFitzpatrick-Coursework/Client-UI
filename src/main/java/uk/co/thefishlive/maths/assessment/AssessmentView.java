package uk.co.thefishlive.maths.assessment;

/**
 *
 */
public enum AssessmentView {

    START("assessments/assessment_start.fxml"),

    QUESTION("assessments/assessment_question.fxml"),

    SUMMARY("assessments/assessment_summary.fxml");

    private final String ui;

    private AssessmentView(String ui) {
        this.ui = ui;
    }

    public String getUiId() {
        return ui;
    }
}
