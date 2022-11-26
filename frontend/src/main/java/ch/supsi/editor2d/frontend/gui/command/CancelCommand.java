package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.frontend.gui.model.CancelHandler;

public class CancelCommand<T extends CancelHandler> extends AbstractCommand<CancelHandler> implements Command {

    protected CancelCommand(T handler) {
        super(handler);
    }

    public static CancelCommand<CancelHandler> create(CancelHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("command handler cannot be null!");
        }

        return new CancelCommand<>(handler);
    }

    @Override
    public void execute() throws NoSuchFieldException {
        if (handler == null) {
            throw new NoSuchFieldException("command handler is null!");
        }

        handler.cancel();
    }

}
