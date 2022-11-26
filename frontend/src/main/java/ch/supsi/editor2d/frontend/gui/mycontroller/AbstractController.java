package ch.supsi.editor2d.frontend.gui.mycontroller;

import ch.supsi.editor2d.frontend.gui.model.Handler;

public abstract class AbstractController<T extends Handler> {
    protected T model;

    protected AbstractController(T model) {
        this.model = model;
    }
}
