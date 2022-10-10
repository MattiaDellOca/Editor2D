package ch.supsi.editor2d.backend.model.filter;

public abstract class MatrixFilter {
    private final float[][] matrix;

    public float[][] getMatrix() {
        return matrix;
    }

    public MatrixFilter(float[][] matrix) {
        this.matrix = matrix;
    }
}
