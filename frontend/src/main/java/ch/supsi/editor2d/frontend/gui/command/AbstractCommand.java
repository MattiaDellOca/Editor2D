package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.frontend.gui.model.Handler;

public abstract class AbstractCommand<T extends Handler> implements Command{
    protected T handler;

    protected AbstractCommand(T handler) {
        this.handler = handler;
    }
}
