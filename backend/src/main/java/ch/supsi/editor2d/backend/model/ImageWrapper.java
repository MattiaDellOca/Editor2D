package ch.supsi.editor2d.backend.model;

public class ImageWrapper {

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
    private final ColorWrapper[][] data;

    /**
     * Constructor
     * @param width image width
     * @param height image height
     * @param data image pixel=color matrix
     */
    public ImageWrapper(int width, int height, ColorWrapper[][] data) {
        this.width = width;
        this.height = height;
        this.data = data;
    }
    public ImageWrapper (ImageWrapper wrapper) {
        this.width = wrapper.getWidth();
        this.height = wrapper.getHeight();

        // Deep-Copy colors
        this.data = new ColorWrapper[wrapper.getHeight()][wrapper.getWidth()];
        for (int i = 0; i < wrapper.getHeight(); i++) {
            for (int j = 0; j < wrapper.getWidth(); j++) {
                data[i][j] = new ColorWrapper(wrapper.getData()[i][j]);
            }
        }
    }

    public ColorWrapper[][] getData() {
        return data;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
