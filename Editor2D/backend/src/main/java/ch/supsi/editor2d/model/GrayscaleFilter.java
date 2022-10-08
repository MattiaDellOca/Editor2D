package ch.supsi.editor2d.model;

public class GrayscaleFilter extends PredefinedMatrixFilter {
    public GrayscaleFilter() {
        matrix = new double[][] { {0.333, 0.333, 0.333}, {0.333, 0.333, 0.333}, {0.333, 0.333, 0.333} };
    }
}
