package ch.supsi.editor2d.backend.model;
import javafx.scene.paint.Color;
public class ImagePPM extends ImageWrapper{

    private int scaleGray;
    public ImagePPM(int width, int height, Color[][] data, int scaleGray) {
        super(width, height, data);
        this.scaleGray = scaleGray;
    }
}
