package ch.supsi.editor2d.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorWrapperTest {

    @Test
    void constructor(){
        ColorWrapper c = new ColorWrapper(1.2f, 0.5f, -1.2f);
        assertEquals(1f, c.getRed());
        assertEquals(0.5f, c.getGreen());
        assertEquals(0.f, c.getBlue());
    }

    @Test
    void equalTest(){
        ColorWrapper c1 = new ColorWrapper(0.2f, 0.5f, 0.745f);
        ColorWrapper c2 = new ColorWrapper(0.2f, 0.5f, 0.745f);
        assertEquals(c1, c2);
    }

    @Test
    void equals() {
        assertEquals(ColorTest.BLACK, ColorTest.BLACK);

        ColorWrapper[] expected = new ColorWrapper[] { ColorTest.BLUE, ColorTest.CYAN, ColorTest.DARK_GRAY };
        assertArrayEquals(expected, new ColorWrapper[] {ColorTest.BLUE, ColorTest.CYAN, ColorTest.DARK_GRAY });

        ColorWrapper[][] matrixExpected = new ColorWrapper[][]{{ColorTest.BLACK, ColorTest.RED, ColorTest.YELLOW},
                {ColorTest.YELLOW, ColorTest.CYAN, ColorTest.DARK_GRAY} };
        assertArrayEquals(matrixExpected, new ColorWrapper[][]{{ColorTest.BLACK, ColorTest.RED, ColorTest.YELLOW},
                {ColorTest.YELLOW, ColorTest.CYAN, ColorTest.DARK_GRAY} });
    }
}