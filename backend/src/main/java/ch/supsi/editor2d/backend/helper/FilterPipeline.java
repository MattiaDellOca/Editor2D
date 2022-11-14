package ch.supsi.editor2d.backend.helper;

import ch.supsi.editor2d.backend.exception.FilterApplyException;
import ch.supsi.editor2d.backend.exception.PipelineException;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.memento.Memento;
import ch.supsi.editor2d.backend.model.pipeline.Pipeline;
import ch.supsi.editor2d.backend.model.pipeline.PipelineObserver;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.backend.model.task.Task;

import java.util.Collection;
import java.util.Iterator;

public class FilterPipeline extends Pipeline<ImageWrapper, FilterTaskResult> {

    public FilterPipeline() {
        super();
    }

    public FilterPipeline(FilterPipeline filterPipeline) {
        super();
        this.tasks.addAll(filterPipeline.tasks);
        this.observers.addAll(filterPipeline.observers);
    }

    @Override
    public FilterTaskResult run(ImageWrapper input) throws PipelineException {
        // Deep copy of input
        Iterator<Task<ImageWrapper, FilterTaskResult>> it = tasks.iterator();
        ImageWrapper image = new ImageWrapper(input);
        final int N_TASKS = tasks.size();

        // Cycle through tasks, one by one using Queue
        for (int i = 1; it.hasNext(); i++) {
            FilterTaskResult result;
            // Get next task
            try {
                result = it.next().execute(image);
            } catch (FilterApplyException e) {
                throw new FilterApplyException("impossible to apply filter: \n The image must be at least 3x3");
            }
            // TODO: check if result is error
            System.out.println("RESULT " + i + ": " + result.getResult());

            // Update progress
            notifyObserversProgress((float) i / N_TASKS);
            // Save image result for next task
            image = result.getResult();
        }

        // Notify observers that pipeline has finished
        notifyObserversFinished();

        // Return FilterTaskResult with final image
        return new FilterTaskResult(image);
    }

    @Override
    public void addObserver(PipelineObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(PipelineObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyObserversFinished() {
        for (PipelineObserver observer : observers) {
            observer.finished();
        }
    }

    @Override
    public void notifyObserversProgress(float progress) {
        for (PipelineObserver observer : observers) {
            observer.update(progress);
        }
    }

    @Override
    public Collection<PipelineObserver> getObservers() {
        return observers;
    }

    @Override
    public Memento<Pipeline<ImageWrapper, FilterTaskResult>> createSnapshot() {
        // Create snapshot (deep copy)
        Memento<Pipeline<ImageWrapper, FilterTaskResult>> memento = new Memento<>(new FilterPipeline(this));
        // Save state
        history.add(memento);

        // Return snapshot
        return memento;
    }

    @Override
    public void restoreSnapshot(Memento<Pipeline<ImageWrapper, FilterTaskResult>> snapshot) {
        if (snapshot == null) {
            throw new IllegalArgumentException("snapshot cannot be null");
        }

        // Restore tasks
        this.tasks.clear();
        this.tasks.addAll(snapshot.getState().getTasks());

        // Restore observers
        this.observers.clear();
        this.observers.addAll(snapshot.getState().getObservers());
    }
}
