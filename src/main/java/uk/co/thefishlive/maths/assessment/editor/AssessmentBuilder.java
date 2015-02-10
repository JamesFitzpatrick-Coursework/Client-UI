package uk.co.thefishlive.maths.assessment.editor;

import uk.co.thefishlive.maths.assessment.Assessment;
import uk.co.thefishlive.maths.assessment.editor.question.QuestionBuilder;
import uk.co.thefishlive.maths.assessment.question.Question;
import uk.co.thefishlive.maths.editor.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class AssessmentBuilder implements Builder<Assessment> {

    private String name;
    private List<QuestionBuilder> questions;

    public AssessmentBuilder() {
        questions = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addQuestion(QuestionBuilder question) {
        this.questions.add(question);
    }

    public int getNextQuestionNumber() {
        return this.questions.size();
    }

    public QuestionBuilder getQuestionBuilder(int i) {
        return this.questions.get(i);
    }

    public Assessment build() {
        List<Question> questions = new ArrayList<>();
        this.questions.stream().forEach(question -> questions.add(question.build()));
        return new Assessment(name, questions);
    }
}
