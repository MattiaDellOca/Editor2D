package ch.supsi.editor2d.backend.model.filter;

import ch.supsi.editor2d.backend.exception.FilterApplyException;
import ch.supsi.editor2d.backend.helper.MatrixMultiplier;
import ch.supsi.editor2d.backend.model.ImageWrapper;

public abstract class TransformMatrixFilter extends MatrixFilter {
    public TransformMatrixFilter(double[][] matrix) {
        super(matrix);
    }

    @Override
    public ImageWrapper apply(ImageWrapper image) throws FilterApplyException {
        return MatrixMultiplier.applyScalarFilter(image, this);
    }
}
