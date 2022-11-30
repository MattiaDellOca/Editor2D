package ch.supsi.editor2d.frontend.gui.receiver;

import ch.supsi.editor2d.frontend.gui.model.DataModel;
import ch.supsi.editor2d.frontend.gui.handler.Observable;
import ch.supsi.editor2d.frontend.gui.handler.UndoRedoHandler;

public class UndoRedoReceiver<T extends Observable> extends AbstractReceiver<DataModel> implements UndoRedoHandler {
    protected UndoRedoReceiver(DataModel model) {
        super(model);
    }

    // factory method
    public static UndoRedoReceiver<DataModel> create(DataModel model) throws InstantiationException {
        if (model == null) {
            throw new InstantiationException("controller model cannot be null!");
        }

        return new UndoRedoReceiver<>(model);
    }

    @Override
    public void undo() {
        model.undo();
    }

    @Override
    public void redo() {
        model.redo();
    }
}
