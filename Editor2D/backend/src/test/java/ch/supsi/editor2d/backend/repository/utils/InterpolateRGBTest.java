package ch.supsi.editor2d.backend.repository.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterpolateRGBTest {

    @Test
    void interpolateRGBtoFloat() {
        assertEquals(0,InterpolateRGB.interpolateRGBtoFloat(0));
        assertEquals(1,InterpolateRGB.interpolateRGBtoFloat(255));
        assertEquals(0.2f,InterpolateRGB.interpolateRGBtoFloat(51));
    }
}