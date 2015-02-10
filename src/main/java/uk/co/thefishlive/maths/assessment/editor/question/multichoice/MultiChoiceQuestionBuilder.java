package uk.co.thefishlive.maths.assessment.editor.question.multichoice;

import uk.co.thefishlive.maths.assessment.QuestionType;
import uk.co.thefishlive.maths.assessment.editor.question.QuestionBuilder;
import uk.co.thefishlive.maths.assessment.question.multichoice.MultiChoiceQuestion;
import uk.co.thefishlive.maths.assessment.question.multichoice.Option;

import java.util.ArrayList;
import java.util.List;

public class MultiChoiceQuestionBuilder extends QuestionBuilder<MultiChoiceQuestion> {

    private List<Option> options;
    private int answer;

    public MultiChoiceQuestionBuilder() {
        this.options = new ArrayList<>();
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public void addOption(Option option) {
        this.options.add(option);
    }

    @Override
    public MultiChoiceQuestion build() {
        MultiChoiceQuestion question = new MultiChoiceQuestion(this.question, this.questionNumber, QuestionType.MULTI_CHOICE, this.options);
        question.setAnswer(this.answer);
        return question;
    }
}
