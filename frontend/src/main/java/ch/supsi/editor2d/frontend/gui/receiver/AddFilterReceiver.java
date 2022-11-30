package ch.supsi.editor2d.frontend.gui.receiver;

import ch.supsi.editor2d.backend.model.filter.Filter;
import ch.supsi.editor2d.frontend.gui.model.AddFilterHandler;
import ch.supsi.editor2d.frontend.gui.model.DataModel;
import ch.supsi.editor2d.frontend.gui.model.Observable;

public class AddFilterReceiver <T extends Observable> extends AbstractReceiver<DataModel> implements AddFilterHandler {
    protected AddFilterReceiver(DataModel model) {
        super(model);
    }

    // factory method
    public static AddFilterReceiver<DataModel> create(DataModel model) throws InstantiationException {
        if (model == null) {
            throw new InstantiationException("controller model cannot be null!");
        }

        return new AddFilterReceiver<>(model);
    }

    @Override
    public void addFilter(Filter filter) {
        model.addFilter(filter);
    }
}
