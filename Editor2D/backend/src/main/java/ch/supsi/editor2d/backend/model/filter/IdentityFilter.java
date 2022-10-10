package ch.supsi.editor2d.backend.model.filter;

import ch.supsi.editor2d.backend.model.ImageWrapper;

public class IdentityFilter extends MatrixFilter implements Applicable {

    public IdentityFilter() {
        super(new float[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        });
    }

    @Override
    public ImageWrapper apply(MatrixFilter filter, ImageWrapper image) {
        return null;
    }
}
