package ch.supsi.editor2d.frontend.gui.event;

import ch.supsi.editor2d.backend.model.ImageWrapper;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 * Event representing an image being updated
 * Used after applying a filter
 */
/*public class ImageUpdatedEvent extends Event {

    public static final EventType<ImageUpdatedEvent> IMAGE_UPDATED = new EventType<>(Event.ANY, "IMAGE_UPDATED");

    public ImageWrapper getImage() { return (ImageWrapper) getSource(); }

    public ImageUpdatedEvent(Object o, EventTarget eventTarget) {
        super(o, eventTarget, IMAGE_UPDATED);
    }
}*/

public class ImageUpdatedEvent extends AbstractEvent {

    public ImageUpdatedEvent(Object source) {
        super(source);
    }

}
