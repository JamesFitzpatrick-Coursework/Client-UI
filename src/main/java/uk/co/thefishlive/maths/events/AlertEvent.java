package uk.co.thefishlive.maths.events;

public class AlertEvent extends Event {

    private final String message;

    public AlertEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
