package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.frontend.gui.model.ExitHandler;

public class ExitCommand<T extends ExitHandler> extends AbstractCommand<ExitHandler> implements Command {

    protected ExitCommand(ExitHandler handler) {
        super(handler);
    }

    // factory method
    public static ExitCommand<ExitHandler> create(ExitHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("command handler cannot be null!");
        }

        return new ExitCommand<>(handler);
    }

    @Override
    public void execute() throws NoSuchFieldException {
        if(handler == null) {
            throw new NoSuchFieldException("command handler is null!");
        }

        handler.exit();
    }

}
