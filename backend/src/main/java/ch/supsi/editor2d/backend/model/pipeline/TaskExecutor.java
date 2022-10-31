package ch.supsi.editor2d.backend.model.pipeline;

import ch.supsi.editor2d.backend.exception.IndexPipelineException;
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

    public List<Task<T,K>> getTasks(){
        return tasks;
    }

    /**
     * Invert the task given with the previous one
     * @param task to be inverted to the previous one
     * @throws IndexPipelineException in case of index errors
     */
    public void invertBeforePositionTask(Task<T,K> task) throws IndexPipelineException {
        int index = tasks.indexOf(task);

        if(index == -1 || index == 0){
            throw new IndexPipelineException("Impossible to invert position with the previous one");
        }

        //Get task before my task
        Task<T,K> taskUp = tasks.get(index-1);

        //Invert the two tasks
        tasks.set(index-1,task);
        tasks.set(index, taskUp);
    }

    /**
     * Invert the task given with the next one
     * @param task to be inverted to the next one
     * @throws IndexPipelineException in case of index errors
     */
    public void invertAfterPositionTask(Task<T,K> task) throws IndexPipelineException{
        int index = tasks.indexOf(task);

        if(index == -1 || index == tasks.size()-1){
            throw new IndexPipelineException("Impossible to invert position with the next one");
        }

        //Get task after my task
        Task<T,K> taskUp = tasks.get(index+1);

        //Invert the two tasks
        tasks.set(index+1,task);
        tasks.set(index, taskUp);

    }

}
