package ch.supsi.editor2d.backend.model;
import javafx.scene.paint.Color;

abstract public class ImageWrapper {

    /**
     *  Image width
     */
    private final int width;

    /**
     * Image height
     */
    private final int height;
    /**
     * Matrix of "pixel"
     */
    private final Color[][] data;

    /**
     * Constructor
     * @param width image width
     * @param height image height
     * @param data image pixel=color matrix
     */
    public ImageWrapper(int width, int height, Color[][] data) {
        this.width = width;
        this.height = height;
        this.data = data;
    }

    public Color[][] getData() {
        return data;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
