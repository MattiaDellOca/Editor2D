package ch.supsi.editor2d.backend.model.pipeline;

import ch.supsi.editor2d.backend.model.memento.Memento;
import ch.supsi.editor2d.backend.model.memento.MementoCaretaker;
import ch.supsi.editor2d.backend.model.memento.Originator;
import ch.supsi.editor2d.backend.model.task.TaskResult;

import java.util.LinkedList;
import java.util.List;

public abstract class Pipeline<T, K extends TaskResult<?>> extends TaskExecutor<T, K> implements ObservablePipeline, Originator<Memento<Pipeline<T, K>>> {
    protected final List<PipelineObserver> observers = new LinkedList<>();
    protected final MementoCaretaker<Pipeline<T, K>> history = new MementoCaretaker<>();

    public Memento<Pipeline<T,K>> undo () throws IllegalStateException {
        Memento<Pipeline<T,K>> memento = history.undo();
        if (memento != null) {
            this.restoreSnapshot(memento);
        }
        return memento;
    }

    public Memento<Pipeline<T,K>> redo () throws IllegalStateException {
        if (!history.canRedo()) {
            throw new IllegalStateException("no snapshot available");
        }

        Memento<Pipeline<T,K>> memento = history.redo();
        if (memento != null) {
            this.restoreSnapshot(memento);
        }
        return memento;
    }
}
