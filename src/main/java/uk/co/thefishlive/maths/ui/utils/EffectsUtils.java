package uk.co.thefishlive.maths.ui.utils;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import uk.co.thefishlive.maths.Main;

/**
 * A builder class to build simple UI transitions
 */
public class EffectsUtils {

    /**
     * Create a simple fade out transition.
     *
     * @param duration the duration of the transition
     * @param node the UI node that this transition applies to
     * @param delay the delay before this transition is played
     * @return the transition
     */
    public static Transition fadeOut(Duration duration, Node node, Duration delay) {
        Transition transition = fadeOut(duration, node);
        transition.setDelay(delay);
        return transition;
    }

    /**
     * Create a simple fade out transition.
     *
     * @param duration the duration of the transition
     * @param node the UI node that this transition applies to
     * @return the transition
     */
    public static Transition fadeOut(Duration duration, Node node) {
        FadeTransition transition = new FadeTransition(duration, node);
        transition.setFromValue(1);
        transition.setToValue(0);
        return transition;
    }

    /**
     * Create a simple fade in transition.
     *
     * @param duration the duration of the transition
     * @param node the UI node that this transition applies to
     * @param delay the delay before this transition is played
     * @return the transition
     */
    public static Transition fadeIn(Duration duration, Node node, Duration delay) {
        Transition transition = fadeIn(duration, node);
        transition.setDelay(delay);
        return transition;
    }

    /**
     * Create a simple fade in transition.
     *
     * @param duration the duration of the transition
     * @param node the UI node that this transition applies to
     * @return the transition
     */
    public static Transition fadeIn(Duration duration, Node node) {
        FadeTransition transition = new FadeTransition(duration, node);
        transition.setFromValue(0);
        transition.setToValue(1);
        return transition;
    }
}
