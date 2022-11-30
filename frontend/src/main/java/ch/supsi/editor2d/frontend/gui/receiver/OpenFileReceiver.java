package ch.supsi.editor2d.frontend.gui.receiver;

import ch.supsi.editor2d.frontend.gui.model.DataModel;
import ch.supsi.editor2d.frontend.gui.handler.Observable;
import ch.supsi.editor2d.frontend.gui.handler.OpenFileHandler;

public class OpenFileReceiver<T extends Observable> extends AbstractReceiver<OpenFileHandler> implements OpenFileHandler {

    protected OpenFileReceiver(DataModel model) {
        super(model);
    }

    // factory method
    public static OpenFileReceiver<DataModel> create(DataModel model) throws InstantiationException {
        if (model == null) {
            throw new InstantiationException("controller model cannot be null!");
        }

        return new OpenFileReceiver<>(model);
    }

    @Override
    public void openFile() {
        model.openFile();
    }
}

