package ch.supsi.editor2d.backend.model.filter;

import ch.supsi.editor2d.backend.helper.MatrixMultiplier;
import ch.supsi.editor2d.backend.model.ImageWrapper;

/**
 * Abstract class that defines filters which work on colors
 */
public abstract class ColorMatrixFilter extends MatrixFilter {
    public ColorMatrixFilter(double[][] matrix) {
        super(matrix);
    }

    @Override
    public ImageWrapper apply(ImageWrapper image) {
        return MatrixMultiplier.applyColorFilter(image, this);
    }
}
