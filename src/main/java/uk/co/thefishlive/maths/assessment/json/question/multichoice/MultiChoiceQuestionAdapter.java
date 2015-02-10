package uk.co.thefishlive.maths.assessment.json.question.multichoice;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import uk.co.thefishlive.maths.assessment.QuestionType;
import uk.co.thefishlive.maths.assessment.question.Question;
import uk.co.thefishlive.maths.assessment.question.multichoice.MultiChoiceQuestion;
import uk.co.thefishlive.maths.assessment.question.multichoice.Option;
import uk.co.thefishlive.maths.json.JsonAdapter;

public class MultiChoiceQuestionAdapter implements JsonAdapter<MultiChoiceQuestion> {
    @Override
    public MultiChoiceQuestion deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject json = jsonElement.getAsJsonObject();

        int questionNumber = json.get("question-number").getAsInt();
        String question = json.get("question").getAsString();
        int answer = MultiChoiceQuestion.NOT_ANSWERED;
        List<Option> options = new ArrayList<>();

        for (JsonElement option : json.getAsJsonArray("options")) {
            options.add(context.deserialize(option, Option.class));
        }

        if (json.has("answer")) answer = json.get("answer").getAsInt();

        MultiChoiceQuestion multiChoiceQuestion = new MultiChoiceQuestion(question, questionNumber, QuestionType.MULTI_CHOICE, options);
        multiChoiceQuestion.setAnswer(answer);
        return multiChoiceQuestion;
    }

    @Override
    public JsonElement serialize(MultiChoiceQuestion question, Type type, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        json.addProperty("question-number", question.getQuestionNumber());
        json.addProperty("question", question.getQuestion());
        json.addProperty("type", question.getType().ordinal());
        if (question.getAnswer() != MultiChoiceQuestion.NOT_ANSWERED) json.addProperty("answer", question.getAnswer());
        json.add("options", context.serialize(question.getOptions()));
        return json;
    }
}
