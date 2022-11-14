package ch.supsi.editor2d.backend.model.pipeline;

import java.util.Collection;

public interface ObservablePipeline {
    void addObserver(PipelineObserver observer);
    void removeObserver(PipelineObserver observer);

    void notifyObserversFinished();

    void notifyObserversProgress(float progress);

    Collection<PipelineObserver> getObservers();
}
