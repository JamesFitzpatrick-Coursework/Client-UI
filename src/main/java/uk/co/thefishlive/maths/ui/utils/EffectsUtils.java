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
 *
 */
public class EffectsUtils {

    public static Transition fadeOut(Duration duration, Node node, Duration delay) {
        Transition transition = fadeOut(duration, node);
        transition.setDelay(delay);
        return transition;
    }

    public static Transition fadeOut(Duration duration, Node node) {
        FadeTransition transition = new FadeTransition(duration, node);
        transition.setFromValue(1);
        transition.setToValue(0);
        return transition;
    }

    public static Transition fadeIn(Duration duration, Node node, Duration delay) {
        Transition transition = fadeIn(duration, node);
        transition.setDelay(delay);
        return transition;
    }

    public static Transition fadeIn(Duration duration, Node node) {
        FadeTransition transition = new FadeTransition(duration, node);
        transition.setFromValue(0);
        transition.setToValue(1);
        return transition;
    }
}
