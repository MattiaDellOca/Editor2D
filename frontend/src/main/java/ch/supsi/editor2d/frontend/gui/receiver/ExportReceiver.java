package ch.supsi.editor2d.frontend.gui.receiver;

import ch.supsi.editor2d.frontend.gui.model.FileExport;
import ch.supsi.editor2d.frontend.gui.handler.ExportHandler;
import ch.supsi.editor2d.frontend.gui.handler.Observable;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import javafx.stage.Stage;

public class ExportReceiver<T extends Observable> extends AbstractReceiver<DataModel> implements ExportHandler {

    protected ExportReceiver(DataModel model) {
        super(model);
    }

    //factory method
    public static ExportReceiver<DataModel> create(DataModel model) throws InstantiationException {
        if (model == null) {
            throw new InstantiationException("controller model cannot be null!");
        }

        return new ExportReceiver<>(model);
    }

    public void exportPage(Stage exportStage) {
        model.exportPage(exportStage);
    }

    public void exportImage(FileExport exportReq) {
        model.exportImage(exportReq);
    }
}
