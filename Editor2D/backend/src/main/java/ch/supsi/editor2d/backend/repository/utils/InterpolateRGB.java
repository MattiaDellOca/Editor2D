package ch.supsi.editor2d.backend.repository.utils;

public class InterpolateRGB {
    private static float valueRGBMax = 255.0f;

    public static float interpolateRGBtoFloat(int valueRGB){
        return valueRGB/valueRGBMax;
    }
}
