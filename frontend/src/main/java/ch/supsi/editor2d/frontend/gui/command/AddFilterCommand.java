package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.backend.model.filter.Filter;

public class AddFilterCommand<T extends FilterReceiver> extends AbstractCommand<FilterReceiver> {

    private Filter filter;

    protected AddFilterCommand(T receiver) {
        super(receiver);
    }

    public static AddFilterCommand<FilterReceiver> create (FilterReceiver receiver){
        if(receiver == null){
            throw new IllegalArgumentException("receiver cannot be null!");
        }

        return new AddFilterCommand<>(receiver);
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    @Override
    public void execute() throws NoSuchFieldException {
        if(receiver == null){
            throw new NoSuchFieldException("receiver is null!");
        }

        receiver.addFilter(filter);
    }
}
