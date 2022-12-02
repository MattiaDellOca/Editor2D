package ch.supsi.editor2d.frontend.gui.memento;

//Immutable object
public class Memento<T> {
    private final T state;

    public Memento(T state) {
        this.state = state;
    }

    public T getState() {
        return state;
    }
}
