package ch.supsi.editor2d.backend.model;

public class ImagePPM extends ImageWrapper {
    private final int RGBScale;

    public ImagePPM(int width, int height, ColorWrapper[][] data, int RGBScale) {
        super(width, height, data);
        this.RGBScale = RGBScale;
    }

    public int getRGBScale() {
        return RGBScale;
    }
}
