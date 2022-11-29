package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.Filter;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.backend.model.task.Task;
import ch.supsi.editor2d.frontend.gui.model.AddFilterHandler;
import ch.supsi.editor2d.frontend.gui.model.RemoveFilterHandler;

public class RemoveFilterCommand<T extends RemoveFilterHandler> extends AbstractCommandParam<RemoveFilterHandler, Task<ImageWrapper, FilterTaskResult>>
        implements CommandParam<Task<ImageWrapper, FilterTaskResult>> {

    protected RemoveFilterCommand(RemoveFilterHandler handler) {
        super(handler);
    }

    public static RemoveFilterCommand<RemoveFilterHandler> create(RemoveFilterHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("command handler cannot be null!");
        }

        return new RemoveFilterCommand<>(handler);
    }

    @Override
    public void execute(Task<ImageWrapper, FilterTaskResult> filter) {
        handler.removeFilter(filter);
    }
}

