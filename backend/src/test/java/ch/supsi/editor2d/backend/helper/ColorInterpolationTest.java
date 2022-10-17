package ch.supsi.editor2d.backend.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorInterpolationTest {

    @Test
    void interpolateRGBtoFloat() {
        assertAll(() -> assertEquals(1.0f, ColorInterpolation.interpolateRGBtoFloat(255), 0.005f),
                () -> assertEquals(0.0f, ColorInterpolation.interpolateRGBtoFloat(0), 0.005f),
                () -> assertEquals(0.686f, ColorInterpolation.interpolateRGBtoFloat(175), 0.005f),
                () -> assertEquals(0.392f, ColorInterpolation.interpolateRGBtoFloat(100), 0.005f),
                () -> assertEquals(0.784f, ColorInterpolation.interpolateRGBtoFloat(200), 0.005f),
                () -> assertEquals(0.2f, ColorInterpolation.interpolateRGBtoFloat(51), 0.005f)
        );
    }

    @Test
    void interpolateRGBtoInt() {
        assertAll(() -> assertEquals(255, ColorInterpolation.interpolateRGBtoInt(1.0f), 1),
                () -> assertEquals(0, ColorInterpolation.interpolateRGBtoInt(0.f), 1),
                () -> assertEquals(175, ColorInterpolation.interpolateRGBtoInt(0.686f), 1),
                () -> assertEquals(100, ColorInterpolation.interpolateRGBtoInt(0.392f), 1),
                () -> assertEquals(200, ColorInterpolation.interpolateRGBtoInt(0.784f), 1),
                () -> assertEquals(51, ColorInterpolation.interpolateRGBtoInt(0.2f), 1));
    }
}