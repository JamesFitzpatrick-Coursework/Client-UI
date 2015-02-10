package uk.co.thefishlive.maths.json;

import com.google.gson.Gson;
import org.junit.BeforeClass;

import uk.co.thefishlive.maths.assessment.Assessment;
import uk.co.thefishlive.maths.assessment.json.AssessmentAdapter;
import uk.co.thefishlive.maths.assessment.json.question.QuestionAdapter;
import uk.co.thefishlive.maths.assessment.json.question.multichoice.MultiChoiceQuestionAdapter;
import uk.co.thefishlive.maths.assessment.question.Question;
import uk.co.thefishlive.maths.assessment.question.multichoice.MultiChoiceQuestion;

/**
 *
 */
public class JsonTestBase {

    protected static Gson GSON;

    @BeforeClass
    public static void setup() {
        if (!GsonInstance.isBuilt()) {
            GsonInstance.registerAdapter(Assessment.class, new AssessmentAdapter());
            GsonInstance.registerAdapter(Question.class, new QuestionAdapter());
            GsonInstance.registerAdapter(MultiChoiceQuestion.class, new MultiChoiceQuestionAdapter());

            GsonInstance.buildInstance();
        }

        GSON = GsonInstance.get();
    }

}
