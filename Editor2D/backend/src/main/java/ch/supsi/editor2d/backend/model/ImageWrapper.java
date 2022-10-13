package ch.supsi.editor2d.backend.model;

public class ImageWrapper {

    private final int width;
    private final int height;
    private final ColorWrapper[][] data;

    public ImageWrapper (ImageWrapper wrapper) {
        this.width = wrapper.getWidth();
        this.height = wrapper.getHeight();
        this.data = wrapper.getData();
    }

    public ImageWrapper (int width, int height, ColorWrapper[][] data) {
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

    public ColorWrapper[][] getData() {
        return data;
    }
}
