package ch.supsi.editor2d.backend.helper;

import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.filter.SharpenFilter;
import ch.supsi.editor2d.backend.model.filter.FlipFilter;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.GrayscaleFilter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MatrixMultiplierTest {
    @Test
    void applyScalarFilter() {
        ImageWrapper sample = new ImageWrapper(2, 3, new ColorWrapper[][]{{ColorWrapper.CYAN, ColorWrapper.BLUE},
                {ColorWrapper.BLACK, ColorWrapper.WHITE}, {ColorWrapper.YELLOW, ColorWrapper.ORANGE}});
        ImageWrapper expected = new ImageWrapper(2, 3, new ColorWrapper[][]{{ColorWrapper.BLUE, ColorWrapper.CYAN}, {
                ColorWrapper.WHITE, ColorWrapper.BLACK}, {ColorWrapper.ORANGE, ColorWrapper.YELLOW}});
        ImageWrapper actual = MatrixMultiplier.applyScalarFilter(sample, new FlipFilter(sample.getWidth()));

        assert actual != null;
        assert expected.getWidth() == actual.getWidth();
        assert expected.getHeight() == actual.getHeight();

        assert Arrays.deepEquals(expected.getData(), actual.getData());
    }

    @Test
    void applyColorFilter() {
        ImageWrapper sample = new ImageWrapper(3, 2, new ColorWrapper[][]{{ColorWrapper.ORANGE, ColorWrapper.CYAN, ColorWrapper.DARK_GRAY},
                {ColorWrapper.RED, ColorWrapper.BLUE, ColorWrapper.YELLOW}});
        ImageWrapper expected = new ImageWrapper(3, 2, new ColorWrapper[][]{{new ColorWrapper(194, 194, 194),
                new ColorWrapper(179, 179, 179), new ColorWrapper(64, 64, 64)}, {new ColorWrapper(76, 76, 76),
                new ColorWrapper(29, 29, 29), new ColorWrapper(226, 226, 226)}});
        ImageWrapper actual = MatrixMultiplier.applyColorFilter(sample, new GrayscaleFilter());

        assert actual != null;
        assert expected.getWidth() == actual.getWidth();
        assert expected.getHeight() == actual.getHeight();

        for (int i = 0; i < expected.getHeight(); i++) {
            for (int t = 0; t < expected.getWidth(); t++) {
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
        ImageWrapper nullReturn1 = MatrixMultiplier.applyKernelFilter(sample1, new SharpenFilter());
        assertNull(nullReturn1);

        ImageWrapper sample2 = new ImageWrapper(2, 3, new ColorWrapper[][]{
                {new ColorWrapper(1.f, 1.f, 1.f), new ColorWrapper(1.f, 1.f, 1.f)},
                {new ColorWrapper(1.f, 1.f, 1.f), new ColorWrapper(1.f, 1.f, 1.f)},
                {new ColorWrapper(1.f, 1.f, 1.f), new ColorWrapper(1.f, 1.f, 1.f)},
        });

        ImageWrapper nullReturn2 = MatrixMultiplier.applyKernelFilter(sample2, new SharpenFilter());
        assertNull(nullReturn2);

        //assert correct image return
        ImageWrapper sample3 = new ImageWrapper(4, 4, new ColorWrapper[][]{
                {new ColorWrapper(1.f, 0.f, 0.f), new ColorWrapper(1.f, 1.f, 0.f), new ColorWrapper(0.f, 1.f, 0.f), new ColorWrapper(0.f, 0.f, 1.f)},
                {new ColorWrapper(1.f, 1.f, 0.f), new ColorWrapper(0.f, 0.5f, 0.f), new ColorWrapper(0.f, 1.f, 0.5f), new ColorWrapper(0.f, 1.f, 0.f)},
                {new ColorWrapper(0.f, 1.f, 0.f), new ColorWrapper(0.5f, 0.f, 0.f), new ColorWrapper(0.f, 0.f, 0.5f), new ColorWrapper(1.f, 1.f, 0.f)},
                {new ColorWrapper(0.f, 0.f, 1.f), new ColorWrapper(0.f, 1.f, 0.f), new ColorWrapper(1.f, 1.f, 0.f), new ColorWrapper(1.f, 0.f, 0.f)},

        });

        ImageWrapper actual = MatrixMultiplier.applyKernelFilter(sample3,new SharpenFilter());

        assertNotNull(actual);
        assertEquals(sample3.getWidth()-2,actual.getWidth());
        assertEquals(sample3.getHeight()-2,actual.getHeight());
        assertEquals(new ColorWrapper(0.f,0.f,0.f),actual.getData()[0][0]);
        assertEquals(new ColorWrapper(0.f,1.f,1.f),actual.getData()[0][1]);
        assertEquals(new ColorWrapper(1.f,0.f,0.f),actual.getData()[1][0]);
        assertEquals(new ColorWrapper(0.f,0.f,1.f),actual.getData()[1][1]);
    }
}
