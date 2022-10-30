package ch.supsi.editor2d.backend.objectPresentation;

import ch.supsi.editor2d.backend.model.filter.Filter;

public class FilterPresentable implements Presentable<Filter> {
    @Override
    public String present(Filter object) {
        return object.getName();
    }
}
