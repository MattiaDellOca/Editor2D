package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.frontend.gui.handler.OkHandler;

public class OkCommand<T extends OkHandler> extends AbstractCommand<OkHandler> implements Command {
    protected OkCommand(OkHandler handler) {
        super(handler);
    }

    // factory method
    public static OkCommand<OkHandler> create(OkHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("command handler cannot be null!");
        }

        return new OkCommand<>(handler);
    }

    @Override
    public void execute() {
        handler.ok();
    }
}
