package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.frontend.gui.handler.ExportHandler;
import ch.supsi.editor2d.frontend.gui.handler.Observable;
import ch.supsi.editor2d.frontend.gui.receiver.ExportReceiver;
import javafx.stage.Stage;

public class ExportPageCommand<T extends ExportHandler>  extends AbstractCommandParam<ExportHandler, Stage>  {

    protected ExportPageCommand(ExportHandler handler) {
        super(handler);
    }

    // factory method
    public static ExportPageCommand<ExportHandler> create(ExportHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("command handler cannot be null!");
        }

        return new ExportPageCommand<>(handler);
    }

    @Override
    public void execute(Stage exportStage) {
        handler.exportPage(exportStage);
    }
}

