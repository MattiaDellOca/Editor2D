package ch.supsi.editor2d.backend.model.pipeline;

public interface PipelineObserver {
    void finished();
    void update(float percentage);
}
