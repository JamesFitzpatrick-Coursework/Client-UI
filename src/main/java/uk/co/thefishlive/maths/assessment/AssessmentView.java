package uk.co.thefishlive.maths.assessment;

/**
 *
 */
public enum AssessmentView {

    START("assessment/assessment_start.fxml"),

    QUESTION("assessment/question_multichoice.fxml"),

    SUMMARY("assessment/assessment_summary.fxml");

    private final String ui;

    private AssessmentView(String ui) {
        this.ui = ui;
    }

    public String getUiId() {
        return ui;
    }
}
