package uk.co.thefishlive.maths.assessment.json.question;

import com.google.gson.*;

import java.lang.reflect.Type;

import uk.co.thefishlive.maths.assessment.QuestionType;
import uk.co.thefishlive.maths.assessment.question.Question;
import uk.co.thefishlive.maths.json.JsonAdapter;

public class QuestionAdapter implements JsonAdapter<Question> {
    @Override
    public Question deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject json = jsonElement.getAsJsonObject();
        QuestionType questionType = QuestionType.values()[json.get("type").getAsInt()];
        return context.deserialize(json, questionType.getHandlerClass());
    }

    @Override
    public JsonElement serialize(Question question, Type type, JsonSerializationContext jsonSerializationContext) {
        // Should never be called
        return new JsonObject();
    }
}
