package uk.co.thefishlive.maths.assessment;

import uk.co.thefishlive.auth.assessments.Assessment;
import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.auth.assessments.questions.QuestionType;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.controllers.assessment.MultiChoiceQuestionController;
import uk.co.thefishlive.maths.ui.controllers.assessment.StartController;
import uk.co.thefishlive.maths.ui.controllers.assessment.SummaryController;
import uk.co.thefishlive.maths.ui.loader.UI;
import uk.co.thefishlive.maths.ui.loader.UILoader;

import java.io.IOException;

public class AssessmentHandler {

    private final Assessment assessment;
    private int questionNumber;

    public AssessmentHandler(Assessment assessment) {
        this.assessment = assessment;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int question) {
        this.questionNumber = question;
    }

    public Question<?> getCurrentQuestion() {
        return assessment.getQuestion(questionNumber);
    }

    public void display(AssessmentView view) throws IOException, ResourceException {
        UI ui;
        switch (view) {
            case QUESTION:
                switch (getCurrentQuestion().getType()) {
                    case MULTI_CHOICE:
                        ui = UILoader.loadUI("ui/assessment/question_multichoice.fxml");
                        ui.getController(MultiChoiceQuestionController.class).setHandler(this);
                        Main.getInstance().setCurrentUI(ui);
                        break;
                    case ANSWER:
                    default:
                        break;
                }
                break;
            case START:
                ui = UILoader.loadUI(view.getUiId());
                ui.getController(StartController.class).setHandler(this);
                Main.getInstance().setCurrentUI(ui);
                break;
            case SUMMARY:
                ui = UILoader.loadUI(view.getUiId());
                ui.getController(SummaryController.class).setHandler(this);
                Main.getInstance().setCurrentUI(ui);
                break;
        }
    }

    public void nextQuestion() throws IOException, ResourceException {
        if (questionNumber == assessment.getQuestions().size()) {
            display(AssessmentView.SUMMARY);
            return;
        }

        questionNumber++;
        display(AssessmentView.QUESTION);
    }

    public void previousQuestion() throws IOException, ResourceException {
        if (questionNumber == 0) {
            display(AssessmentView.START);
            return;
        }

        questionNumber--;
        display(AssessmentView.QUESTION);
    }
}
