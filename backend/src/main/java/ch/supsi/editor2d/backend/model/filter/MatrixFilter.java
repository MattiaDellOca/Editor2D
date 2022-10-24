package ch.supsi.editor2d.backend.model.filter;

/**
 * Abstract class that define a generic filter based on a matrix
 */
public abstract class MatrixFilter extends Filter {
    protected final double[][] matrix;

    /**
     * Name of the filter, which will be displayed in the frontend
     */

    public MatrixFilter(double[][] matrix, String name) {
        super(name);
        this.matrix = matrix;
    }

    public double[][] getMatrix() {
        return matrix;
    }
}
