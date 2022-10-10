package ch.supsi.editor2d.backend.model;

// TODO: 10/10/22 singleton 
public class GrayscaleFilter extends MatrixFilter {
    public GrayscaleFilter() {
        super(new double[][] { {0.333, 0.333, 0.333}, {0.333, 0.333, 0.333}, {0.333, 0.333, 0.333} });
    }
}
