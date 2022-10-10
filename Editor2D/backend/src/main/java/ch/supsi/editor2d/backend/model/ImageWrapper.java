package ch.supsi.editor2d.backend.model;

import javafx.scene.paint.Color;

public class ImageWrapper {

    private final int width;
    private final int height;
    private final Color[][] pixels;

    public ImageWrapper (ImageWrapper wrapper) {
        this.width = wrapper.getWidth();
        this.height = wrapper.getHeight();
        this.pixels = wrapper.getPixels();
    }

    public ImageWrapper (int width, int height, Color[][] pixels) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color[][] getPixels() {
        return pixels;
    }
}
