package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.frontend.gui.handler.Handler;

public abstract class AbstractCommandParam<T extends Handler, C> implements CommandParam<C> {
    protected T handler;

    protected AbstractCommandParam(T handler) {
        this.handler = handler;
    }
}
