package ch.supsi.editor2d.backend.model.filter;

import ch.supsi.editor2d.backend.exception.FilterApplyException;
import ch.supsi.editor2d.backend.model.ColorTest;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import ch.supsi.editor2d.backend.model.filter.Filter;
import ch.supsi.editor2d.backend.model.filter.FlipFilter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FlipFilterTest {
    @Test
    void applyCorrect() throws FilterApplyException {
        ImageWrapper sample = new ImageWrapper(3, 3,
                new ColorWrapper[][]{{ColorTest.WHITE, ColorTest.YELLOW, ColorTest.CYAN},
                {ColorTest.GRAY, ColorTest.GREEN, ColorTest.ORANGE},
                {ColorTest.WHITE, ColorTest.BLUE, ColorTest.RED}
        });

        ImageWrapper expected = new ImageWrapper(3, 3,
                new ColorWrapper[][]{{ColorTest.CYAN, ColorTest.YELLOW, ColorTest.WHITE},
                {ColorTest.ORANGE, ColorTest.GREEN, ColorTest.GRAY},
                {ColorTest.RED, ColorTest.BLUE, ColorTest.WHITE}
        });

        Filter flip = new FlipFilter();
        ImageWrapper result = flip.apply(sample);

        assert Arrays.deepEquals(expected.getData(), result.getData());
    }

    @Test
    void applyIncorrect() throws FilterApplyException {
        ImageWrapper sample = new ImageWrapper(3, 3,
                new ColorWrapper[][]{{ColorTest.WHITE, ColorTest.YELLOW, ColorTest.CYAN},
                        {ColorTest.GRAY, ColorTest.GREEN, ColorTest.ORANGE},
                        {ColorTest.WHITE, ColorTest.BLUE, ColorTest.RED}
                });

        ImageWrapper expected = new ImageWrapper(3, 3,
                new ColorWrapper[][]{{ColorTest.BLACK, ColorTest.YELLOW, ColorTest.WHITE},
                        {ColorTest.ORANGE, ColorTest.BLACK, ColorTest.GRAY},
                        {ColorTest.RED, ColorTest.BLUE, ColorTest.BLACK}
                });

        Filter flip = new FlipFilter();
        ImageWrapper result = flip.apply(sample);

        assertFalse(Arrays.deepEquals(expected.getData(), result.getData()));
    }
}