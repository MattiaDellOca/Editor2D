package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.backend.helper.FilterPipeline;
import ch.supsi.editor2d.backend.model.filter.Filter;

public class AddFilterCommand implements Command {
    private final FilterReceiver receiver; //FIXME è giusto avere AddFilterReceiver o meglio un interfaccia Receiver?
    private Filter filter;

    public AddFilterCommand(FilterReceiver receiver) {
        this.receiver = receiver;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    @Override
    public void execute() {
        receiver.addFilter(filter);
    }
}
