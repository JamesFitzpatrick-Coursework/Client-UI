package uk.co.thefishlive.maths.json;

import org.junit.Test;
import uk.co.thefishlive.maths.assessment.Assessment;
import uk.co.thefishlive.maths.assessment.QuestionType;
import uk.co.thefishlive.maths.assessment.question.Question;
import uk.co.thefishlive.maths.assessment.question.multichoice.Option;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 */
public class AssessmentJsonTest extends JsonTestBase {

    public static final String ASSESSMENT_JSON = "{\"name\":\"Test Assessment\",\"questions\":[{\"question-number\":1,\"question\":\"A Test Question\",\"type\":0,\"options\":[{\"text\":\"Answer 1\"},{\"text\":\"Answer 2\"},{\"text\":\"Answer 3\"},{\"text\":\"Answer 4\"}]},{\"question-number\":2,\"question\":\"A Test Question 2\",\"type\":0,\"options\":[{\"text\":\"Answer 1\"},{\"text\":\"Answer 2\"},{\"text\":\"Answer 3\"},{\"text\":\"Answer 4\"}]}]}";

    @Test
    public void testSerialization() throws ReflectiveOperationException {
        List<Option> options = new ArrayList<>(Arrays.asList(
                new Option("Answer 1"),
                new Option("Answer 2"),
                new Option("Answer 3"),
                new Option("Answer 4")
        ));

        Question question1 = QuestionType.MULTI_CHOICE.createInstance(
                new Class[]{String.class, int.class, QuestionType.class, List.class},
                new Object[]{"A Test Question", 1, QuestionType.MULTI_CHOICE, options}
        );

        Question question2 = QuestionType.MULTI_CHOICE.createInstance(
                new Class[]{String.class, int.class, QuestionType.class, List.class},
                new Object[]{"A Test Question 2", 2, QuestionType.MULTI_CHOICE, options}
        );

        Assessment assessment = new Assessment(
                "Test Assessment",
                Arrays.asList(
                        question1,
                        question2
                )
        );

        String json = GSON.toJson(assessment);
        System.out.println(json);
    }

    @Test
    public void testDeserialization() {
        String json = ASSESSMENT_JSON;
        Assessment assessment = GSON.fromJson(json, Assessment.class);
        System.out.println(assessment);
    }
}
