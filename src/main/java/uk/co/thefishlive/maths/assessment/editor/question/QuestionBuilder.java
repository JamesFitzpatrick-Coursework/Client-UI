package uk.co.thefishlive.maths.assessment.editor.question;

import uk.co.thefishlive.maths.assessment.question.Question;
import uk.co.thefishlive.maths.editor.Builder;

/**
 *
 */
public abstract class QuestionBuilder<T extends Question> implements Builder<T> {

    protected String question;
    protected int questionNumber;

    public QuestionBuilder() {
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setQuestionNumber(int number) {
        this.questionNumber = number;
    }

    @Override
    public abstract T build();
}
