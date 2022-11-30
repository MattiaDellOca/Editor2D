package ch.supsi.editor2d.frontend.gui.receiver;

import ch.supsi.editor2d.frontend.gui.model.DataModel;
import ch.supsi.editor2d.frontend.gui.handler.Observable;
import ch.supsi.editor2d.frontend.gui.handler.OpenFileHandler;

public class OpenFileReceiver<T extends Observable> extends AbstractReceiver<OpenFileHandler> implements OpenFileHandler {

    protected OpenFileReceiver(OpenFileHandler handler) {
        super(handler);
    }

    // factory method
    public static OpenFileReceiver<DataModel> create(OpenFileHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("command handler cannot be null!");
        }

        return new OpenFileReceiver<>(handler);
    }

    @Override
    public void openFile() {
        model.openFile();
    }
}

