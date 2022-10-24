package ch.supsi.editor2d.frontend.gui.event;

import ch.supsi.editor2d.frontend.gui.event.util.FileExport;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class FileExportEvent extends Event {
    public static final EventType<FileExportEvent> FILE_EXPORT_REQUEST = new EventType<>(Event.ANY, "FILE_EXPORT_REQUEST");

    private final FileExport export;

    public FileExportEvent(FileExport export, EventTarget eventTarget) {
        super(export, eventTarget, FILE_EXPORT_REQUEST);
        this.export = export;
    }

    public FileExport getExport() {
        return export;
    }
}
