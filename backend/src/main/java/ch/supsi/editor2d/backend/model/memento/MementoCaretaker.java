package ch.supsi.editor2d.backend.model.memento;

import java.util.LinkedList;

/**
 * Caretaker class, able to store and restore the state of an Originator object.
 *
 * Redo and undo operations are supported since the caretaker stores the history inside linked list with a pointer to the current state.
 * @param <T>
 */
public class MementoCaretaker<T>  {

    public static final int MAX_HISTORY_SIZE = 10;

    protected final LinkedList<Memento<T>> history = new LinkedList<>();

    private int current = -1;

    public void add(Memento<T> memento) {
        if (history.size() == MAX_HISTORY_SIZE) {
            history.removeFirst();
            current--;
        }

        history.add(memento);
        current++;
    }

    public Memento<T> undo() {
        if (current > 0) {
            current--;
            return history.get(current);
        }

        return null;
    }

    public Memento<T> redo() {
        if (current < history.size() - 1) {
            current++;
            return history.get(current);
        }

        return null;
    }

    public Memento<T> getCurrent() {
        return history.get(current);
    }

    public boolean canUndo() {
        return current > 0;
    }

    public boolean canRedo() {
        return current < history.size() - 1;
    }

    public void clear() {
        history.clear();
        current = -1;
    }

    public int size() {
        return history.size();
    }
}
