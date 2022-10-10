package ch.supsi.editor2d.backend.model.filter;

// TODO: 10/10/22 singleton
public class GrayscaleFilter extends ScalarMatrixFilter {
    public GrayscaleFilter() {
        super(new double[][] { {0.333, 0.333, 0.333}, {0.333, 0.333, 0.333}, {0.333, 0.333, 0.333} });
    }
}
