package ch.supsi.editor2d.backend.helper;

import ch.supsi.editor2d.backend.exception.FilterApplyException;
import ch.supsi.editor2d.backend.model.ColorTest;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.filter.FlipFilter;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.GrayscaleFilter;
import ch.supsi.editor2d.backend.model.filter.SharpenFilter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MatrixMultiplierTest {
    @Test
    void applyColorFilter() {
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


    @Test
    void applyKernelFilter() {
        //assert null
        ImageWrapper sample1 = new ImageWrapper(3, 2, new ColorWrapper[][]{
                {new ColorWrapper(1.f, 1.f, 1.f), new ColorWrapper(1.f, 1.f, 1.f), new ColorWrapper(1.f, 1.f, 1.f)},
                {new ColorWrapper(1.f, 1.f, 1.f), new ColorWrapper(1.f, 1.f, 1.f), new ColorWrapper(1.f, 1.f, 1.f)},
        });
        try {
            MatrixMultiplier.applyKernelFilter(sample1, new SharpenFilter());
            fail();
        } catch (FilterApplyException ignored) {
        }
        ImageWrapper sample2 = new ImageWrapper(2, 3, new ColorWrapper[][]{
                {new ColorWrapper(1.f, 1.f, 1.f), new ColorWrapper(1.f, 1.f, 1.f)},
                {new ColorWrapper(1.f, 1.f, 1.f), new ColorWrapper(1.f, 1.f, 1.f)},
                {new ColorWrapper(1.f, 1.f, 1.f), new ColorWrapper(1.f, 1.f, 1.f)},
        });

        try {
            MatrixMultiplier.applyKernelFilter(sample2, new SharpenFilter());
            fail();
        } catch (FilterApplyException ignored) {
        }

        //assert correct image return
        ImageWrapper sample3 = new ImageWrapper(4, 4, new ColorWrapper[][]{
                {new ColorWrapper(1.f, 0.f, 0.f), new ColorWrapper(1.f, 1.f, 0.f), new ColorWrapper(0.f, 1.f, 0.f), new ColorWrapper(0.f, 0.f, 1.f)},
                {new ColorWrapper(1.f, 1.f, 0.f), new ColorWrapper(0.f, 0.5f, 0.f), new ColorWrapper(0.f, 1.f, 0.5f), new ColorWrapper(0.f, 1.f, 0.f)},
                {new ColorWrapper(0.f, 1.f, 0.f), new ColorWrapper(0.5f, 0.f, 0.f), new ColorWrapper(0.f, 0.f, 0.5f), new ColorWrapper(1.f, 1.f, 0.f)},
                {new ColorWrapper(0.f, 0.f, 1.f), new ColorWrapper(0.f, 1.f, 0.f), new ColorWrapper(1.f, 1.f, 0.f), new ColorWrapper(1.f, 0.f, 0.f)},

        });

        try {
            ImageWrapper actual = MatrixMultiplier.applyKernelFilter(sample3,new SharpenFilter());
            assertEquals(sample3.getWidth()-2,actual.getWidth());
            assertEquals(sample3.getHeight()-2,actual.getHeight());
            assertEquals(new ColorWrapper(0.f,0.f,0.f),actual.getData()[0][0]);
            assertEquals(new ColorWrapper(0.f,1.f,1.f),actual.getData()[0][1]);
            assertEquals(new ColorWrapper(1.f,0.f,0.f),actual.getData()[1][0]);
            assertEquals(new ColorWrapper(0.f,0.f,1.f),actual.getData()[1][1]);
        } catch (FilterApplyException e) {
            fail();
        }

    }
}
