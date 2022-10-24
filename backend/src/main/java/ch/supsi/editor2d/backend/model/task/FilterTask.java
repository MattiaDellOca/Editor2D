package ch.supsi.editor2d.backend.model.task;

import ch.supsi.editor2d.backend.exception.FilterApplyException;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.Filter;

public class FilterTask implements Task<ImageWrapper, FilterTaskResult> {

    private final Filter filter;

    public FilterTask (Filter filter) {
        this.filter = filter;
    }

    @Override
    public FilterTaskResult execute(ImageWrapper input) throws FilterApplyException {
        return new FilterTaskResult(filter.apply(input));
    }

    public MatrixFilter getFilter() {
        return filter;
    }
}
