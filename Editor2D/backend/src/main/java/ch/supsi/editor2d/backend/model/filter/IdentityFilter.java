package ch.supsi.editor2d.backend.model.filter;

import ch.supsi.editor2d.backend.model.ImageWrapper;

public class IdentityFilter extends MatrixFilter implements ScalarFilterApplicator {
    public IdentityFilter() {
        super(new double[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        });
    }
}
