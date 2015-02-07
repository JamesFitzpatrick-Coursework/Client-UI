package uk.co.thefishlive.maths.assessment;

import java.lang.reflect.Constructor;
import uk.co.thefishlive.maths.assessment.question.Question;
import uk.co.thefishlive.maths.assessment.question.multichoice.MultiChoiceQuestion;

public enum QuestionType {

    MULTI_CHOICE(MultiChoiceQuestion.class),
    SHORT_ANSWER(null),
    ;

    private final Class<? extends Question> clazz;

    private QuestionType(Class<? extends Question> clazz) {
        this.clazz = clazz;
    }

    public Question createInstance(Class[] types, Object[] args) throws ReflectiveOperationException {
        Constructor<? extends Question> ctor = clazz.getConstructor(types);
        return ctor.newInstance(args);
    }

    public Class<? extends Question> getHandlerClass() {
        return this.clazz;
    }
}
