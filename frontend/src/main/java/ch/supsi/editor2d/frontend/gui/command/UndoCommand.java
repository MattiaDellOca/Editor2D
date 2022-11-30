package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.frontend.gui.handler.UndoRedoHandler;

public class UndoCommand<T extends UndoRedoHandler> extends AbstractCommand<UndoRedoHandler>  {

    protected UndoCommand(T handler) {
        super(handler);
    }

    public static UndoCommand<UndoRedoHandler> create(UndoRedoHandler handler) throws InstantiationException {
        if (handler == null) {
            throw new InstantiationException("command handler cannot be null!");
        }

        return new UndoCommand<>(handler);
    }

    @Override
    public void execute() {
        handler.undo();
    }
}