package ch.supsi.editor2d.backend.model.filter;

import ch.supsi.editor2d.backend.helper.MatrixMultiplier;
import ch.supsi.editor2d.backend.model.ImageWrapper;

public abstract class ColorMatrixFilter extends MatrixFilter {
    public ColorMatrixFilter(double[][] matrix) {
        super(matrix);
    }

    @Override
    public ImageWrapper apply(ImageWrapper image) {
        // TODO: 13/10/22 create algorithm for applying pixel color manipulation 
        return MatrixMultiplier.applyScalarFilter(image, this);
    }
}
