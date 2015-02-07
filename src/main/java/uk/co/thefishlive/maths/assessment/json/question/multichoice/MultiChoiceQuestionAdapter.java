package uk.co.thefishlive.maths.assessment.json.question.multichoice;

import com.google.gson.*;

import java.lang.reflect.Type;

import uk.co.thefishlive.maths.assessment.question.multichoice.MultiChoiceQuestion;
import uk.co.thefishlive.maths.assessment.question.multichoice.Option;
import uk.co.thefishlive.maths.json.JsonAdapter;

public class MultiChoiceQuestionAdapter implements JsonAdapter<MultiChoiceQuestion> {
    @Override
    public MultiChoiceQuestion deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject json = jsonElement.getAsJsonObject();

        return null;
    }

    @Override
    public JsonElement serialize(MultiChoiceQuestion question, Type type, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        json.addProperty("question-number", question.getQuestionNumber());
        json.addProperty("question", question.getQuestion());
        if (question.getAnswer() != MultiChoiceQuestion.NOT_ANSWERED) json.addProperty("answer", question.getAnswer());
        json.add("options", context.serialize(question.getOptions()));
        return json;
    }
}
