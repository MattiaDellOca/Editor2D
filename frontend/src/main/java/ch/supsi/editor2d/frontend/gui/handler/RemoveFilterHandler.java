package ch.supsi.editor2d.frontend.gui.handler;

import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.backend.model.task.Task;

public interface RemoveFilterHandler extends Handler {
    void removeFilter(Task<ImageWrapper, FilterTaskResult> filter);
}

