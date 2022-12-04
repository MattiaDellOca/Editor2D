package ch.supsi.editor2d.backend.model;

import ch.supsi.editor2d.backend.helper.ColorInterpolation;

import java.util.Objects;

/**
 * Class used for representing an RGB color
 * Some default colors are defined
 * Colors are sorted in float values ranging from 0 to 1
 */
public class ColorWrapper {
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

    public ColorWrapper (final ColorWrapper c) {
        this.r = c.r;
        this.g = c.g;
        this.b = c.b;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColorWrapper that = (ColorWrapper) o;
        return Float.compare(that.r, r) == 0 && Float.compare(that.g, g) == 0 && Float.compare(that.b, b) == 0;
    }
}
