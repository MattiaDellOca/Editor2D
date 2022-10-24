package ch.supsi.editor2d.backend.model.filter;

/**
 * Abstract class that define a generic filter based on a matrix
 */
public abstract class MatrixFilter extends Filter {
    protected final double[][] matrix;

    public MatrixFilter(double[][] matrix) {
        this.matrix = matrix;
    }

    public double[][] getMatrix() {
        return matrix;
    }
}
