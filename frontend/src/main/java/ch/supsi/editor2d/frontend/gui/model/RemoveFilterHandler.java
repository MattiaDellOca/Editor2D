package ch.supsi.editor2d.frontend.gui.model;

import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.Filter;
import ch.supsi.editor2d.backend.model.task.FilterTaskResult;
import ch.supsi.editor2d.backend.model.task.Task;

public interface RemoveFilterHandler extends Handler {
    void removeFilter(Task<ImageWrapper, FilterTaskResult> filter);
}

