package ch.supsi.editor2d.backend.model.memento;

public class Memento<T> {
    private final T state;

    public Memento(T state) {
        this.state = state;
    }

    public T getState() {
        return state;
    }
}
