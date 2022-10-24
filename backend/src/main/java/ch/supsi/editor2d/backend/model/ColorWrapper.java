package ch.supsi.editor2d.backend.model;

import ch.supsi.editor2d.backend.helper.ColorInterpolation;

import java.util.Objects;

public class ColorWrapper {
    public static final ColorWrapper white = new ColorWrapper(255, 255, 255);
    public static final ColorWrapper WHITE;
    public static final ColorWrapper lightGray;
    public static final ColorWrapper LIGHT_GRAY;
    public static final ColorWrapper gray;
    public static final ColorWrapper GRAY;
    public static final ColorWrapper darkGray;
    public static final ColorWrapper DARK_GRAY;
    public static final ColorWrapper black;
    public static final ColorWrapper BLACK;
    public static final ColorWrapper red;
    public static final ColorWrapper RED;
    public static final ColorWrapper pink;
    public static final ColorWrapper PINK;
    public static final ColorWrapper orange;
    public static final ColorWrapper ORANGE;
    public static final ColorWrapper yellow;
    public static final ColorWrapper YELLOW;
    public static final ColorWrapper green;
    public static final ColorWrapper GREEN;
    public static final ColorWrapper magenta;
    public static final ColorWrapper MAGENTA;
    public static final ColorWrapper cyan;
    public static final ColorWrapper CYAN;
    public static final ColorWrapper blue;
    public static final ColorWrapper BLUE;

    private final float r;
    private final float g;
    private final float b;

    public ColorWrapper (float r, float g, float b) {
        // Force values in range [0, 1]
        if (r < 0)
            r = 0;
        else if (r > 1)
            r = 1;
        if (g < 0)
            g = 0;
        else if (g > 1)
            g = 1;
        if(b < 0)
            b = 0;
        else if(b > 1)
            b = 1;

        this.r = r;
        this.g = g;
        this.b = b;
    }

    public ColorWrapper (int r, int g, int b) {
        this.r = ColorInterpolation.interpolateRGBtoFloat(r);
        this.g = ColorInterpolation.interpolateRGBtoFloat(g);
        this.b = ColorInterpolation.interpolateRGBtoFloat(b);
    }

    public float getRed() {
        return r;
    }

    public float getGreen() {
        return g;
    }

    public float getBlue() {
        return b;
    }

    public float[] getRGB() {
        return new float[] { r, g, b };
    }

    static {
        WHITE = white;
        lightGray = new ColorWrapper(192, 192, 192);
        LIGHT_GRAY = lightGray;
        gray = new ColorWrapper(128, 128, 128);
        GRAY = gray;
        darkGray = new ColorWrapper(64, 64, 64);
        DARK_GRAY = darkGray;
        black = new ColorWrapper(0, 0, 0);
        BLACK = black;
        red = new ColorWrapper(255, 0, 0);
        RED = red;
        pink = new ColorWrapper(255, 175, 175);
        PINK = pink;
        orange = new ColorWrapper(255, 200, 0);
        ORANGE = orange;
        yellow = new ColorWrapper(255, 255, 0);
        YELLOW = yellow;
        green = new ColorWrapper(0, 255, 0);
        GREEN = green;
        magenta = new ColorWrapper(255, 0, 255);
        MAGENTA = magenta;
        cyan = new ColorWrapper(0, 255, 255);
        CYAN = cyan;
        blue = new ColorWrapper(0, 0, 255);
        BLUE = blue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColorWrapper that = (ColorWrapper) o;
        return Float.compare(that.r, r) == 0 && Float.compare(that.g, g) == 0 && Float.compare(that.b, b) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b);
    }

    @Override
    public String toString() {
        return "ColorWrapper{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }
}
