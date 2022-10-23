package ch.supsi.editor2d.backend.model.filter;

import ch.supsi.editor2d.backend.helper.MatrixMultiplier;
import ch.supsi.editor2d.backend.model.ImageWrapper;

public abstract class TransformMatrixFilter extends MatrixFilter implements Applicable {
    public TransformMatrixFilter(double[][] matrix, String name) {
        super(matrix,name);
    }

    @Override
    public ImageWrapper apply(ImageWrapper image) {
        return MatrixMultiplier.applyScalarFilter(image, this);
    }
}
