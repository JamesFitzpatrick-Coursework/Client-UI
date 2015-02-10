package uk.co.thefishlive.maths.assessment.question.multichoice;

import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.util.List;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.assessment.QuestionType;
import uk.co.thefishlive.maths.assessment.question.Question;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.controllers.assessment.MultiChoiceQuestionController;
import uk.co.thefishlive.maths.ui.loader.UI;
import uk.co.thefishlive.maths.ui.loader.UILoader;

public class MultiChoiceQuestion extends Question<Integer> {

    public static int NOT_ANSWERED = -1;

    private List<Option> options;
    private int answer = NOT_ANSWERED;

    public MultiChoiceQuestion(String question, int questionNumber, QuestionType type, List<Option> options) {
        super(question, questionNumber, type);

        this.options = options;
    }

    public List<Option> getOptions() {
        return ImmutableList.copyOf(options);
    }

    @Override
    public void display() throws IOException, ResourceException {
        UI ui = UILoader.loadUI("ui/assessment/question_multichoice.fxml");

        MultiChoiceQuestionController controller = ui.getController(MultiChoiceQuestionController.class);
        controller.setQuestion(this.assessment);

        Main.getInstance().setCurrentUI(ui);
    }

    @Override
    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    @Override
    public Integer getAnswer() {
        return this.answer;
    }

    @Override
    public boolean isAnswered() {
        return this.answer != NOT_ANSWERED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MultiChoiceQuestion that = (MultiChoiceQuestion) o;

        if (answer != that.answer) return false;
        if (options != null ? !options.equals(that.options) : that.options != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = options != null ? options.hashCode() : 0;
        result = 31 * result + answer;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MultiChoiceQuestion{");
        sb.append("question-number=").append(questionNumber);
        sb.append(", question='").append(question);
        sb.append("', options=").append(options);
        sb.append(", answer=").append(answer);
        sb.append('}');
        return sb.toString();
    }


}
