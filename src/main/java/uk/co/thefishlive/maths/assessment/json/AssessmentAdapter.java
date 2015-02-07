package uk.co.thefishlive.maths.assessment.json;

import com.google.gson.*;

import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;
import uk.co.thefishlive.maths.assessment.Assessment;
import uk.co.thefishlive.maths.assessment.question.Question;
import uk.co.thefishlive.maths.json.JsonAdapter;

public class AssessmentAdapter implements JsonAdapter<Assessment> {
    @Override
    public Assessment deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject json = jsonElement.getAsJsonObject();

        String name = json.get("name").getAsString();
        List<Question> questions = new ArrayList<>();

        for (JsonElement question : json.getAsJsonArray("questions")) {
            questions.add(context.deserialize(question, Question.class));
        }

        return new Assessment(name, questions);
    }

    @Override
    public JsonElement serialize(Assessment assessment, Type type, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        json.addProperty("name", assessment.getName());
        json.add("questions", context.serialize(assessment.getQuestions()));
        return json;
    }
}
