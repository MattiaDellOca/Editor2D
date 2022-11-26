package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.frontend.gui.model.UndoRedoHandler;

public class RedoCommand<T extends UndoRedoHandler> extends AbstractCommand<UndoRedoHandler> implements Command {

    protected RedoCommand(T handler) {
        super(handler);
    }

    public static RedoCommand<UndoRedoHandler> create(UndoRedoHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("command handler cannot be null!");
        }

        return new RedoCommand<>(handler);
    }

    @Override
    public void execute() {
        handler.redo();
    }

}
