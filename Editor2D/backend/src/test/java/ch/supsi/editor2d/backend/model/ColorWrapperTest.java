package ch.supsi.editor2d.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorWrapperTest {
    @Test
    void equals() {
        assertEquals(ColorWrapper.BLACK, ColorWrapper.black);

        ColorWrapper[] expected = new ColorWrapper[] { ColorWrapper.BLUE, ColorWrapper.CYAN, ColorWrapper.DARK_GRAY };
        assertArrayEquals(expected, new ColorWrapper[] {ColorWrapper.BLUE, ColorWrapper.CYAN, ColorWrapper.DARK_GRAY });

        ColorWrapper[][] matrixExpected = new ColorWrapper[][]{{ColorWrapper.BLACK, ColorWrapper.RED, ColorWrapper.YELLOW},
                {ColorWrapper.YELLOW, ColorWrapper.CYAN, ColorWrapper.DARK_GRAY} };
        assertArrayEquals(matrixExpected, new ColorWrapper[][]{{ColorWrapper.BLACK, ColorWrapper.RED, ColorWrapper.YELLOW},
                {ColorWrapper.YELLOW, ColorWrapper.CYAN, ColorWrapper.DARK_GRAY} });
    }
}