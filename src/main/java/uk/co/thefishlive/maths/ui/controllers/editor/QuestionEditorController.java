package uk.co.thefishlive.maths.ui.controllers.editor;

import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.auth.assessments.questions.QuestionBuilder;
import uk.co.thefishlive.auth.assessments.questions.multichoice.MultichoiceQuestionBuilder;
import uk.co.thefishlive.maths.ui.Controller;

/**
 * Represents a controller that handles editor behaviour.
 */
public abstract class QuestionEditorController extends Controller {

    protected QuestionCreateCallback callback;
    protected int questionNumber;
    protected MultichoiceQuestionBuilder builder;

    /**
     * Set the editor information for this controller
     *
     * @param builder the builder for this editor instance
     * @param questionNumber the question number for the instance
     */
    public void setEditorInfo(QuestionBuilder builder, int questionNumber) {
        this.questionNumber = questionNumber;
        this.builder = (MultichoiceQuestionBuilder) builder;
    }

    /**
     * Set the creation callback for this controller.
     *
     * @param callback the new creation callback
     */
    public void setCallback(QuestionCreateCallback callback) {
        this.callback = callback;
    }

    public static interface QuestionCreateCallback {
        public void questionCreated(Question question) throws Exception;
    }
}
