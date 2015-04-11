package uk.co.thefishlive.maths.events;

import com.google.common.eventbus.EventBus;

public class EventController {

    private static final EventController instance = new EventController();

    private EventBus eventBus = new EventBus();

    private EventController() {}

    public static EventController getInstance() {
        return instance;
    }

    public void postEvent(Event event) {
        eventBus.post(event);
    }

    public void registerHandler(Listener listener) {
        this.eventBus.register(listener);
    }
}
