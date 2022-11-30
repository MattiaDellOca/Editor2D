package ch.supsi.editor2d.frontend.gui.handler;

import ch.supsi.editor2d.backend.model.filter.Filter;

public interface AddFilterHandler extends Handler {
    void addFilter(Filter filter);
}
