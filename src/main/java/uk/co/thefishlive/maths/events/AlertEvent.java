package uk.co.thefishlive.maths.events;

/**
 * Created by James on 03/02/2015.
 */
public class AlertEvent extends Event {

    private final String message;

    public AlertEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
