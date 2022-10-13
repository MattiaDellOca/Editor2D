package ch.supsi.editor2d.backend.helper;

public class ColorInterpolation {
    private static float valueRGBMax = 255.0f;

    public static float interpolateRGBtoFloat(int valueRGB){
        return valueRGB / valueRGBMax;
    }

    public static float interpolateRGBtoInt(float valueRGB){
        return valueRGB * valueRGBMax;
    }
}
