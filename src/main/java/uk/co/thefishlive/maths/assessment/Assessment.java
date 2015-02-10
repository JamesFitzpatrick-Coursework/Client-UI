package uk.co.thefishlive.maths.assessment;

import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.assessment.exceptions.EndOfAssessmentException;
import uk.co.thefishlive.maths.assessment.question.Question;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.controllers.assessment.StartController;
import uk.co.thefishlive.maths.ui.controllers.assessment.SummaryController;
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
                UI summaryUi = UILoader.loadUI("ui/assessment/question_summary.fxml");
                summaryUi.getController(SummaryController.class).setAssessment(this);
                Main.getInstance().setCurrentUI(summaryUi);
                break;
            case START:
                UI startUi = UILoader.loadUI("ui/assessment/question_start.fxml");
                startUi.getController(StartController.class).setAssessment(this);
                Main.getInstance().setCurrentUI(startUi);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Assessment that = (Assessment) o;

        if (current != that.current) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (questions != null ? !questions.equals(that.questions) : that.questions != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (questions != null ? questions.hashCode() : 0);
        result = 31 * result + current;
        return result;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "name='" + name + '\'' +
                ", questions=" + questions +
                ", current=" + current +
                '}';
    }
}
