package ch.supsi.editor2d.backend.model.filter;

import ch.supsi.editor2d.backend.model.filter.MatrixFilter;

// TODO: 10/10/22 singleton
public class GrayscaleFilter extends MatrixFilter implements ScalarFilterApplicator {
    public GrayscaleFilter() {
        super(new double[][] { {0.333, 0.333, 0.333}, {0.333, 0.333, 0.333}, {0.333, 0.333, 0.333} });
    }
}
