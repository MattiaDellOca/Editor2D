package ch.supsi.editor2d.backend.model;

import java.awt.*;

public class ImagePPM extends ImageWrapper{
    /**
     * Constructor
     * @param width
     * @param height
     * @param data
     */
    public ImagePPM(int width, int height, Color[][] data) {
        super(width, height, data);
    }
}
