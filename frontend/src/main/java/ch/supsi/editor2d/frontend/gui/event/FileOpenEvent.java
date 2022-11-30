package ch.supsi.editor2d.frontend.gui.event;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

import java.io.File;

//TODO: delete this class
public class FileOpenEvent extends Event {

    public static final EventType<FileOpenEvent> FILE_DROPPED = new EventType<>(Event.ANY, "FILE_DROPPED");

    public File getFile() {
        return (File) getSource();
    }

    public FileOpenEvent(File o, EventTarget eventTarget) {
        super(o, eventTarget, FILE_DROPPED);
    }
}
