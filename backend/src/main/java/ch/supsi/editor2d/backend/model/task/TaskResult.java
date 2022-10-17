package ch.supsi.editor2d.backend.model.task;

public abstract class TaskResult<T> {
    /**
     * Task generic result value
     */
    private T result;

    /**
     * Task generic result value getter
     * @return T The task result
     */
    public T getResult () {
        return result;
    }

    /**
     * Task generic result value setter
     * @param result The new task result value
     */
    protected void setResult(T result) {
        this.result = result;
    }
}
