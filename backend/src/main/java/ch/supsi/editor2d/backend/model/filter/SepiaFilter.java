package ch.supsi.editor2d.backend.model.filter;

/**
 * Transform a colored image in sepia scale
 */
public class SepiaFilter extends ColorMatrixFilter {
    public SepiaFilter() {
        super(new double[][] { {0.393, 0.769, 0.189},
                {0.349, 0.686, 0.168},
                {0.272, 0.534, 0.131} });
    }

}
