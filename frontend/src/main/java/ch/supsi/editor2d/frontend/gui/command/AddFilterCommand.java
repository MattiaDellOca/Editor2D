package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.backend.model.filter.Filter;
import ch.supsi.editor2d.frontend.gui.model.AddFilterHandler;

public class AddFilterCommand<T extends AddFilterHandler> extends AbstractCommandParam<AddFilterHandler, Filter>
        implements CommandParam<Filter> {

    protected AddFilterCommand(AddFilterHandler handler) {
        super(handler);
    }

    public static AddFilterCommand<AddFilterHandler> create(AddFilterHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("command handler cannot be null!");
        }

        return new AddFilterCommand<>(handler);
    }

    @Override
    public void execute(Filter filter) {
        handler.addFilter(filter);
    }
}
