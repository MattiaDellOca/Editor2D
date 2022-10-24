package ch.supsi.editor2d.backend.model.pipeline;

import ch.supsi.editor2d.backend.exception.PipelineException;
import ch.supsi.editor2d.backend.model.task.Task;
import ch.supsi.editor2d.backend.model.task.TaskResult;

public interface Executor<T, K extends TaskResult<?>> {
    K run(T input) throws PipelineException;
    void clear();
    void add(Task<T, K> task);
    void remove(Task<T, K> task);
}
