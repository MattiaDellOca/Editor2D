package ch.supsi.editor2d.frontend.gui.model;

import ch.supsi.editor2d.backend.model.memento.Memento;
import ch.supsi.editor2d.backend.model.memento.MementoCaretaker;
import ch.supsi.editor2d.backend.model.memento.Originator;
import ch.supsi.editor2d.backend.model.task.Task;
import ch.supsi.editor2d.backend.model.task.TaskResult;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ObservableTaskList<T, K extends TaskResult<?>> extends MementoCaretaker<ObservableList<Task<T, K>>> implements Originator<Memento<ObservableList<Task<T, K>>>> {

    private final ObservableList<Task<T, K>> tasks;

    public ObservableList<Task<T, K>> getList() {
        return tasks;
    }

    public ObservableTaskList() {
        super();
        tasks = FXCollections.observableArrayList();
    }

    public ObservableTaskList(ObservableList<Task<T, K>> tasks) {
        super();
        this.tasks = tasks;
    }

    public void add(Task<T, K> task) {
        tasks.add(task);
    }

    public void remove(Task<T, K> task) {
        tasks.remove(task);
    }

    public void clear() {
        tasks.clear();
    }

    public void addAll(List<Task<T, K>> tasks) {
        this.tasks.addAll(tasks);
    }

    public Task<T, K> get(int index) {
        // check index
        if (index < 0 || index >= tasks.size()) {
            return null;
        } else {
            return tasks.get(index);
        }
    }

    public int size() {
        return tasks.size();
    }

    @Override
    public Memento<ObservableList<Task<T, K>>> createSnapshot() {
        super.history.add(new Memento<>(tasks));
        return super.getCurrent();
    }

    @Override
    public void restoreSnapshot(Memento<ObservableList<Task<T, K>>> snapshot) throws IllegalArgumentException {
        if (snapshot == null) {
            throw new IllegalArgumentException("snapshot cannot be null");
        }
        tasks.clear();
        tasks.addAll(snapshot.getState());
    }
}
