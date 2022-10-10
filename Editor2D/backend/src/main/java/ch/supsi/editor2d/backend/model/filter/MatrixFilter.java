package ch.supsi.editor2d.backend.model.filter;

public abstract class MatrixFilter {
    protected final double[][] matrix;

    public MatrixFilter(double[][] matrix) {
        this.matrix = matrix;
    }

    public double[][] getMatrix() {
        return matrix;
    }
}
