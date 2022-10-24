package ch.supsi.editor2d.backend.model.filter;

import ch.supsi.editor2d.backend.helper.MatrixMultiplier;
import ch.supsi.editor2d.backend.model.ImageWrapper;

/**
 * Abstract class that defines filter which work on mathematical transformation
 */
public abstract class TransformMatrixFilter extends MatrixFilter implements Applicable {
    public TransformMatrixFilter(double[][] matrix) {
        super(matrix);
    }

    @Override
    public ImageWrapper apply(ImageWrapper image) {
        return MatrixMultiplier.applyTransformFilter(image, this);
    }
}
