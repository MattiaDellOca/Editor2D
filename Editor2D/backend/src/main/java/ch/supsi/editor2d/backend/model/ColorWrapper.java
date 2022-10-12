package ch.supsi.editor2d.backend.model;

public class ColorWrapper {
    private final float red;
    private final float green;
    private final float blue;

    public ColorWrapper(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }
}
