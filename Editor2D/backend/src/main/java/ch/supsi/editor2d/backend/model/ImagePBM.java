package ch.supsi.editor2d.backend.model;

import java.awt.*;

public class ImagePBM extends ImageWrapper{


    /**
     * Constructor
     * @param width
     * @param height
     * @param data
     */
    public ImagePBM(int width, int height, Color[][] data) {
        super(width, height, data);
    }
}
