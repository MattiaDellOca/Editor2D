package ch.supsi.editor2d.backend.model.filter;

import ch.supsi.editor2d.backend.helper.MatrixMultiplier;
import ch.supsi.editor2d.backend.model.ColorTest;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GrayscaleFilterTest {

    @Test
    void applyCorrect() {
        ImageWrapper sample = new ImageWrapper(3, 2, new ColorWrapper[][] { { ColorTest.ORANGE, ColorTest.CYAN, ColorTest.DARK_GRAY },
                { ColorTest.RED, ColorTest.BLUE, ColorTest.YELLOW } });


        ImageWrapper expected = new ImageWrapper(3, 2, new ColorWrapper[][] { { new ColorWrapper(194, 194, 194),
                new ColorWrapper(179, 179, 179), new ColorWrapper(64, 64, 64) }, { new ColorWrapper(76, 76, 76),
                new ColorWrapper(29, 29, 29), new ColorWrapper(226, 226, 226) }});

        ImageWrapper actual = MatrixMultiplier.applyColorFilter(sample, new GrayscaleFilter());

        assert expected.getWidth() == actual.getWidth();
        assert expected.getHeight() == actual.getHeight();

        for(int i = 0; i < expected.getHeight(); i ++) {
            for(int t = 0; t < expected.getWidth(); t ++) {
                assertArrayEquals(expected.getData()[i][t].getRGB(), actual.getData()[i][t].getRGB(), 0.005f);
            }
        }
    }

}