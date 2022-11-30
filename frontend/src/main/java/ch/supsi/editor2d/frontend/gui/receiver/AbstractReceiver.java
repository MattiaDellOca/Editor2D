package ch.supsi.editor2d.frontend.gui.receiver;

import ch.supsi.editor2d.frontend.gui.handler.Handler;

public abstract class AbstractReceiver<T extends Handler> {
    protected T model;

    protected AbstractReceiver(T model) {
        this.model = model;
    }
}
