package ch.supsi.editor2d.frontend.gui.receiver;

import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.backend.model.task.Task;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import ch.supsi.editor2d.frontend.gui.handler.Observable;
import ch.supsi.editor2d.frontend.gui.handler.RemoveFilterHandler;

public class RemoveFilterReceiver<T extends Observable> extends AbstractReceiver<DataModel> implements RemoveFilterHandler {
    protected RemoveFilterReceiver(DataModel model) {
        super(model);
    }

    // factory method
    public static RemoveFilterReceiver<DataModel> create(DataModel model) throws InstantiationException {
        if (model == null) {
            throw new InstantiationException("controller model cannot be null!");
        }

        return new RemoveFilterReceiver<>(model);
    }

    @Override
    public void removeFilter(Task<ImageWrapper, FilterTaskResult> filter) {
        model.removeFilter(filter);
    }

}
