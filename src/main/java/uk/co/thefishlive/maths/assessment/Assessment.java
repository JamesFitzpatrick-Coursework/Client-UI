package uk.co.thefishlive.maths.assessment;

import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.util.List;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.assessment.exceptions.EndOfAssessmentException;
import uk.co.thefishlive.maths.assessment.question.Question;
import uk.co.thefishlive.maths.assessment.question.multichoice.MultiChoiceQuestion;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.controllers.assessment.MultiChoiceQuestionController;
import uk.co.thefishlive.maths.ui.loader.UI;
import uk.co.thefishlive.maths.ui.loader.UILoader;

public class Assessment {

    private String name;
    private List<Question> questions;
    private int current;

    public Assessment(String name, List<Question> questions) {
        this.name = name;
        this.questions = questions;
        this.questions.stream().forEach(question -> question.setAssessment(this));
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return ImmutableList.copyOf(questions);
    }

    public <T extends Question> T getCurrentQuestion(Class<T> clazz) {
        return (T) this.questions.get(current);
    }

    public void display(AssessmentView view) throws IOException, ResourceException {
        switch (view) {
            case QUESTION:
                getCurrentQuestion(Question.class).display();
                break;
            case SUMMARY:
                break;
            case START:
                break;
        }
    }

    public void nextQuestion() throws IOException, ResourceException, EndOfAssessmentException {
        if (this.current == this.questions.size() -1) {
            throw new EndOfAssessmentException();
        }

        this.current++;
        display(AssessmentView.QUESTION);
    }

    public void previousQuestion() throws IOException, ResourceException, EndOfAssessmentException {
        if (this.current == 0) {
            throw new EndOfAssessmentException();
        }
        this.current--;
        display(AssessmentView.QUESTION);
    }
}
