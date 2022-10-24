package ch.supsi.editor2d.backend.model;

import ch.supsi.editor2d.backend.exception.FilterApplyException;
import ch.supsi.editor2d.backend.model.filter.Filter;
import ch.supsi.editor2d.backend.model.filter.FlipFilter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FlipFilterTest {
    @Test
    void applyCorrect() throws FilterApplyException {
        ImageWrapper sample = new ImageWrapper(3, 3,
                new ColorWrapper[][]{{ColorWrapper.WHITE, ColorWrapper.YELLOW, ColorWrapper.CYAN},
                {ColorWrapper.GRAY, ColorWrapper.GREEN, ColorWrapper.ORANGE},
                {ColorWrapper.WHITE, ColorWrapper.BLUE, ColorWrapper.RED}
        });

        ImageWrapper expected = new ImageWrapper(3, 3,
                new ColorWrapper[][]{{ColorWrapper.CYAN, ColorWrapper.YELLOW, ColorWrapper.WHITE},
                {ColorWrapper.ORANGE, ColorWrapper.GREEN, ColorWrapper.GRAY},
                {ColorWrapper.RED, ColorWrapper.BLUE, ColorWrapper.WHITE}
        });

        Filter flip = new FlipFilter();
        ImageWrapper result = flip.apply(sample);

        assert Arrays.deepEquals(expected.getData(), result.getData());
    }

    @Test
    void applyIncorrect() throws FilterApplyException {
        ImageWrapper sample = new ImageWrapper(3, 3,
                new ColorWrapper[][]{{ColorWrapper.WHITE, ColorWrapper.YELLOW, ColorWrapper.CYAN},
                        {ColorWrapper.GRAY, ColorWrapper.GREEN, ColorWrapper.ORANGE},
                        {ColorWrapper.WHITE, ColorWrapper.BLUE, ColorWrapper.RED}
                });

        ImageWrapper expected = new ImageWrapper(3, 3,
                new ColorWrapper[][]{{ColorWrapper.BLACK, ColorWrapper.YELLOW, ColorWrapper.WHITE},
                        {ColorWrapper.ORANGE, ColorWrapper.BLACK, ColorWrapper.GRAY},
                        {ColorWrapper.RED, ColorWrapper.BLUE, ColorWrapper.BLACK}
                });

        Filter flip = new FlipFilter();
        ImageWrapper result = flip.apply(sample);

        assertFalse(Arrays.deepEquals(expected.getData(), result.getData()));
    }
}