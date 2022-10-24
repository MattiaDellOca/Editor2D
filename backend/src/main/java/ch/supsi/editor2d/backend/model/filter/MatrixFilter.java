package ch.supsi.editor2d.backend.model.filter;

/**
 * Abstract class that define a generic filter based on a matrix
 */
public abstract class MatrixFilter extends Filter {
    protected final double[][] matrix;

    /**
     * Name of the filter, which will be displayed in the frontend
     */
    private final String name;

    public MatrixFilter(double[][] matrix, String name) {
        this.matrix = matrix;
        this.name = name;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public String getName() {
        return name;
    }
}
