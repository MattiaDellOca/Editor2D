package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.frontend.gui.event.util.FileExport;
import ch.supsi.editor2d.frontend.gui.handler.ExportHandler;
import ch.supsi.editor2d.frontend.gui.receiver.ExportReceiver;

public class ExportImageCommand<T extends ExportHandler> extends AbstractCommandParam<ExportHandler, FileExport>  {

    protected ExportImageCommand(ExportHandler handler) {
        super(handler);
    }

    // factory method
    public static ExportImageCommand<ExportHandler> create(ExportHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("command receiver cannot be null!");
        }

        return new ExportImageCommand<>(handler);
    }

    @Override
    public void execute(FileExport exportReq) {
        handler.exportImage(exportReq);
    }
}
