package ch.supsi.editor2d.backend.model.pipeline;

import ch.supsi.editor2d.backend.model.task.TaskResult;

import java.util.LinkedList;
import java.util.List;

public abstract class Pipeline<T, K extends TaskResult<?>> extends TaskExecutor<T, K> {
}
