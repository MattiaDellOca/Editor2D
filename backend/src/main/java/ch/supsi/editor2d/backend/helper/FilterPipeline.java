package ch.supsi.editor2d.backend.helper;

import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.pipeline.Pipeline;
import ch.supsi.editor2d.backend.model.pipeline.PipelineObserver;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;

public final class FilterPipeline extends Pipeline<ImageWrapper, FilterTaskResult> {
    public FilterPipeline() {
        super();
    }

    @Override
    public FilterTaskResult run(ImageWrapper input) {
        // Deep copy of input
        ImageWrapper image = new ImageWrapper(input);

        final int NTASKS = tasks.size();

        // Cycle through tasks, one by one using Queue
        for (int i = 1; !tasks.isEmpty(); i++) {
            // Get next task
            FilterTaskResult result = tasks.poll().execute(image);

            // TODO: check if result is error
            System.out.println("RESULT " + i + ": " + result.getResult());

            // Update progress
            notifyObserversProgress((float) i / NTASKS);

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
}
