package ch.supsi.editor2d.backend.model.filter;

public abstract class MatrixFilter implements Applicable {
    protected final double[][] matrix;

    public MatrixFilter(double[][] matrix) {
        this.matrix = matrix;
    }

    public double[][] getMatrix() {
        return matrix;
    }
}
