package ch.supsi.editor2d.backend.helper;

public final class ColorInterpolation {
    private static final float VALUE_RGB_MAX = 255.0f;

    public static float interpolateRGBtoFloat(int valueRGB){
        return valueRGB / VALUE_RGB_MAX;
    }

    public static int interpolateRGBtoInt(float valueRGB){
        return (int) (valueRGB * VALUE_RGB_MAX);
    }
}
