package uk.co.thefishlive.maths.ui.utils;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

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

    /**
     * Shake a panel in the event of an error.
     *
     * @param pane the pane to shake
     */
    public static void panelShake(Pane pane) {
        int duration = 100;
        int count = 2;

        TranslateTransition transition1 = new TranslateTransition(Duration.millis(duration), pane);
        transition1.setFromX(0);
        transition1.setToX(-5);
        transition1.setInterpolator(Interpolator.LINEAR);

        TranslateTransition transition2 = new TranslateTransition(Duration.millis(duration), pane);
        transition2.setFromX(-5);
        transition2.setToX(5);
        transition2.setDelay(Duration.millis(duration));
        transition2.setInterpolator(Interpolator.LINEAR);
        transition2.setCycleCount(count);

        TranslateTransition transition3 = new TranslateTransition(Duration.millis(duration), pane);
        transition3.setToX(0);
        transition3.setDelay(Duration.millis((count + 1) * duration));
        transition3.setInterpolator(Interpolator.LINEAR);

        transition1.play();
        transition2.play();
        transition3.play();
    }
}
