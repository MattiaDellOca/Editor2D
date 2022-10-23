package ch.supsi.editor2d.backend.model.filter;

// TODO: 10/10/22 singleton
public class GrayscaleFilter extends ColorMatrixFilter {

    public GrayscaleFilter() {
        super(new double[][] { {0.299, 0.587, 0.114}, {0.299, 0.587, 0.114}, {0.299, 0.587, 0.114} }, "Grayscale");
    }
}
