package ch.supsi.editor2d.frontend.gui.command;

import ch.supsi.editor2d.backend.helper.FilterPipeline;
import ch.supsi.editor2d.backend.model.filter.Filter;
import ch.supsi.editor2d.backend.model.task.FilterTask;


/**
 * Filter receiver
 * Business logic for add/remove filter to the pipeline
 */
public class FilterReceiver {
    private final FilterPipeline filterPipeline;

    public FilterReceiver(FilterPipeline filterPipeline) {
        this.filterPipeline = filterPipeline;
    }

    public void addFilter(Filter filter){
        filterPipeline.add(new FilterTask(filter));
    }
}
