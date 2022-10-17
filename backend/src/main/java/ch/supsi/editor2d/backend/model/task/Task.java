package ch.supsi.editor2d.backend.model.task;

public interface Task<T, K extends TaskResult<?>> {
    K execute(T input);
}
