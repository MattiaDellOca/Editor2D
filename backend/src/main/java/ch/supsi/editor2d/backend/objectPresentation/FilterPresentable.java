package ch.supsi.editor2d.backend.objectPresentation;

import ch.supsi.editor2d.backend.model.filter.MatrixFilter;

public class FilterPresentable implements Presentable<MatrixFilter> {
    @Override
    public String present(MatrixFilter object) {
        return object.getName();
    }
}
