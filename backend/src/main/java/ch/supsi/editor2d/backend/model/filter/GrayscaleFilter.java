package ch.supsi.editor2d.backend.model.filter;

/**
 * Transform a colored image in grayscale
 */
public class GrayscaleFilter extends ColorMatrixFilter {
    public GrayscaleFilter() {
        // Predefined matrix for grayscale
        super(new double[][] { {0.299, 0.587, 0.114}, {0.299, 0.587, 0.114}, {0.299, 0.587, 0.114} });
    }
}
