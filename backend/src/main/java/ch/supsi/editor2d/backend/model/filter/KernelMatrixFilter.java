package ch.supsi.editor2d.backend.model.filter;

import ch.supsi.editor2d.backend.model.ImageWrapper;

public abstract class KernelMatrixFilter extends  MatrixFilter implements Applicable{

    public KernelMatrixFilter(double[][] matrix) {
        super(matrix);
    }

    @Override
    public ImageWrapper apply(ImageWrapper image) {
        return null;
    }
}
