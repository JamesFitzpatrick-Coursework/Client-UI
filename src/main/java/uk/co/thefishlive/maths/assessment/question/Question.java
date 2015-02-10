package uk.co.thefishlive.maths.assessment.question;

import com.google.common.base.Preconditions;
import java.io.IOException;
import uk.co.thefishlive.maths.assessment.Assessment;
import uk.co.thefishlive.maths.assessment.QuestionType;
import uk.co.thefishlive.maths.resources.exception.ResourceException;

public abstract class Question<T> {

    protected String question;
    protected int questionNumber;
    protected QuestionType type;
    protected Assessment assessment;

    public Question(String question, int questionNumber, QuestionType type) {
        this.question = question;
        this.questionNumber = questionNumber;
        this.type = type;
    }

    public void setAssessment(Assessment assessment) {
        Preconditions.checkState(this.assessment == null, "Cannot reset assessment instance");
        this.assessment = assessment;
    }

    public Assessment getAssessment() {
        return this.assessment;
    }

    public String getQuestion() {
        return question;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public QuestionType getType() {
        return type;
    }

    public abstract void display() throws IOException, ResourceException;

    public abstract void setAnswer(T answer);

    public abstract T getAnswer();

    public abstract boolean isAnswered();
}
