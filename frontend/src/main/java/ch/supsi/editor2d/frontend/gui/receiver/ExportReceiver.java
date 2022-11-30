package ch.supsi.editor2d.frontend.gui.receiver;

import ch.supsi.editor2d.frontend.gui.event.util.FileExport;
import ch.supsi.editor2d.frontend.gui.handler.ExportHandler;
import javafx.stage.Stage;

public class ExportReceiver {
    protected ExportHandler handler;

    protected ExportReceiver(ExportHandler handler) {
        this.handler = handler;
    }

    //factory method
    public static ExportReceiver create(ExportHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("receiver handler cannot be null!");
        }

        return new ExportReceiver(handler);
    }

    public void exportPage(Stage exportStage) {
        handler.exportPage(exportStage);
    }

    public void exportImage(FileExport exportReq) {
        handler.exportImage(exportReq);
    }
}
