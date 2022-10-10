package ch.supsi.editor2d.backend.model;

import ch.supsi.editor2d.backend.model.filter.FlipFilter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FlipFilterTest {
    @Test
    void constructor() {
        assertTrue(Arrays.deepEquals(new double[][] { {0, 0, 1}, {0, 1, 0}, {1, 0, 0}}, new FlipFilter(3).getMatrix()));
    }
}