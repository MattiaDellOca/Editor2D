package ch.supsi.editor2d.backend.model;

import javafx.scene.paint.Color;

public class ImageWrapper {
    private final int width;
    private final int height;
    private final Color[][] data;

    public ImageWrapper(int width, int height, Color[][] data) {
        this.width = width;
        this.height = height;
        this.data = data;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color[][] getData() {
        return data;
    }
}
