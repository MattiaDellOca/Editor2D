package ch.supsi.editor2d.backend.model.filter;

import ch.supsi.editor2d.backend.helper.MatrixMultiplier;
import ch.supsi.editor2d.backend.model.ColorTest;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SepiaFilterTest {

    @Test
    void applyCorrect() {
        ImageWrapper sample = new ImageWrapper(3, 2, new ColorWrapper[][] { { ColorTest.ORANGE, ColorTest.CYAN, ColorTest.DARK_GRAY },
                { ColorTest.RED, ColorTest.BLUE, ColorTest.YELLOW } });


        ImageWrapper expected = new ImageWrapper(3, 2, new ColorWrapper[][] { { new ColorWrapper(254, 226, 176),
                new ColorWrapper(244, 218, 167), new ColorWrapper(86, 77, 60) }, { new ColorWrapper(100, 89, 69),
                new ColorWrapper(48, 43, 33), new ColorWrapper(255, 255, 206) }});

        ImageWrapper actual = MatrixMultiplier.applyColorFilter(sample, new SepiaFilter());

        assert expected.getWidth() == actual.getWidth();
        assert expected.getHeight() == actual.getHeight();

        for(int i = 0; i < expected.getHeight(); i ++) {
            for(int t = 0; t < expected.getWidth(); t ++) {
                assertArrayEquals(expected.getData()[i][t].getRGB(), actual.getData()[i][t].getRGB(), 0.03f);
            }
        }
    }


}