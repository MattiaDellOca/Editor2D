package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.frontend.gui.event.util.FileExport;
import ch.supsi.editor2d.frontend.gui.receiver.ExportReceiver;

public class ExportImageCommand implements CommandParam<FileExport> {
    protected ExportReceiver receiver;

    protected ExportImageCommand(ExportReceiver receiver) {
        super();
        this.receiver = receiver;
    }

    // factory method
    public static ExportImageCommand create(ExportReceiver receiver) throws InstantiationException {
        if (receiver == null) {
            throw new InstantiationException("command receiver cannot be null!");
        }

        return new ExportImageCommand(receiver);
    }

    @Override
    public void execute(FileExport exportReq) {
        receiver.exportImage(exportReq);
    }
}
