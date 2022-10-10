package ch.supsi.editor2d.backend.model.filter;

import ch.supsi.editor2d.backend.helper.MatrixMultiplier;
import ch.supsi.editor2d.backend.model.ImageWrapper;

public abstract class ScalarMatrixFilter extends MatrixFilter implements Applicable {
    public ScalarMatrixFilter(double[][] matrix) {
        super(matrix);
    }

    @Override
    public ImageWrapper apply(ImageWrapper image) {
        return MatrixMultiplier.applyScalarFilter(image, this);
    }
}
