package ch.supsi.editor2d.backend.model.task;

import ch.supsi.editor2d.backend.exception.PipelineException;

public interface Task<T, K extends TaskResult<?>> {
    K execute(T input) throws PipelineException;
}
