package ch.supsi.editor2d.backend.model;
import javafx.scene.paint.Color;
public class ImagePGM extends ImageWrapper{

    private int scaleGray;
    public ImagePGM(int width, int height, Color[][] data, int scaleGray) {
        super(width, height, data);
        this.scaleGray = scaleGray;
    }
}
