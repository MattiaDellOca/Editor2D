package ch.supsi.editor2d.backend.model.pipeline;

import ch.supsi.editor2d.backend.model.task.Task;
import ch.supsi.editor2d.backend.model.task.TaskResult;

import java.util.Collection;

public interface Executor<T, K extends TaskResult<?>> {
    K run(T input);
    void clear();
    void add(Task<T, K> task);
    void add(Collection<Task<T, K>> tasks);

    void remove(Task<T, K> task);
}
