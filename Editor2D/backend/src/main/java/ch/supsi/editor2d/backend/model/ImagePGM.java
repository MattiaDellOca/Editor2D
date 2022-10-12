package ch.supsi.editor2d.backend.model;
public class ImagePGM extends ImageWrapper{

    private int scaleGray;
    public ImagePGM(int width, int height, ColorWrapper[][] data, int scaleGray) {
        super(width, height, data);
        this.scaleGray = scaleGray;
    }
}
