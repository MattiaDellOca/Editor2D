package ch.supsi.editor2d.backend.model.filter;

public abstract class MatrixFilter implements Applicable {
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
