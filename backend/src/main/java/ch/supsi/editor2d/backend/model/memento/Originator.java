package ch.supsi.editor2d.backend.model.memento;

public interface Originator<T extends Memento<?>> {
    T createSnapshot();

    void restoreSnapshot(T snapshot);
}
