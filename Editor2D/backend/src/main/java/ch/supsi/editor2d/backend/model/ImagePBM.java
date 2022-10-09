package ch.supsi.editor2d.backend.model;
import javafx.scene.paint.Color;
public class ImagePBM extends ImageWrapper{

    public ImagePBM(int width, int height, Color[][] data) {
        super(width, height, data);
    }
}
