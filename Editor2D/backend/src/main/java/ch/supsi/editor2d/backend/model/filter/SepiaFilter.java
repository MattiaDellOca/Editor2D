package ch.supsi.editor2d.backend.model.filter;

// TODO: 10/10/22 singleton
public class SepiaFilter extends ScalarMatrixFilter {
    public SepiaFilter() {
        super(new double[][] { {0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131} });
    }
}
