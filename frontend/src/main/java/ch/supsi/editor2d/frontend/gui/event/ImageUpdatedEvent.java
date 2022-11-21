package ch.supsi.editor2d.frontend.gui.event;

import ch.supsi.editor2d.backend.model.ImageWrapper;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 * Event representing an image being updated
 * Used after applying a filter
 */
public class ImageUpdatedEvent extends AbstractEvent {

    public ImageUpdatedEvent(Object source) {
        super(source);
    }

}
