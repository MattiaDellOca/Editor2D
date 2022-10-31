package ch.supsi.editor2d.backend.model.pipeline;

import ch.supsi.editor2d.backend.exception.PipelineException;
import ch.supsi.editor2d.backend.model.task.Task;
import ch.supsi.editor2d.backend.model.task.TaskResult;

import java.util.LinkedList;
import java.util.List;

public abstract class TaskExecutor<T, K extends TaskResult<?>> implements Executor<T, K> {

    protected final List<Task<T, K>> tasks = new LinkedList<>();

    public abstract K run(T startInput) throws PipelineException;

    @Override
    public void clear() {
        this.tasks.clear();
    }

    @Override
    public void add(Task<T, K> task) {
        this.tasks.add(task);
    }

    @Override
    public void remove(Task<T, K> task) {
        this.tasks.remove(task);
    }

    public boolean isEmpty () {
        return this.tasks.isEmpty();
    }

    public List<Task<T,K>> getQueue(){
        return tasks;
    }
}
