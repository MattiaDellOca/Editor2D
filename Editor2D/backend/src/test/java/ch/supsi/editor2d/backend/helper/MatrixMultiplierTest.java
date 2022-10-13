package ch.supsi.editor2d.backend.helper;

import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.filter.FlipFilter;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MatrixMultiplierTest {
    @Test
    void multiplyMatrices() {
        double[][] A = new double[][] { {1, 2, 3}, {4, 5, 6}, {7, 8, 9} };
        double[][] B = new double[][] { {1, 1, 1}, {1, 1, 1}, {1, 1, 1} };
        double[][] R = new double[][] { {6, 6, 6}, {15, 15, 15}, {24, 24, 24} };
        assertTrue(Arrays.deepEquals(R, MatrixMultiplier.multiplyMatrices(A, B)));
        B = new double[][] { {0, 0, 1}, {0, 1, 0}, {1, 0, 0} };
        R = new double[][] { {3, 2, 1}, {6, 5, 4}, {9, 8, 7} };
        assertTrue(Arrays.deepEquals(R, MatrixMultiplier.multiplyMatrices(A, B)));
        A = new double[][] { {6, 5}, {3, 6}, {7, 1} };
        B = new double[][] { {0, 1}, {1, 0} };
        R = new double[][] { {5, 6}, {6, 3}, {1, 7} };
        assertTrue(Arrays.deepEquals(R, MatrixMultiplier.multiplyMatrices(A, B)));
    }

    @Test
    void applyScalarFilter() {
        ImageWrapper imageWrapper = new ImageWrapper(2, 3, new ColorWrapper[][] { {ColorWrapper.CYAN, ColorWrapper.BLUE},
                {ColorWrapper.BLACK, ColorWrapper.WHITE}, {ColorWrapper.YELLOW, ColorWrapper.ORANGE}});
        assertTrue(Arrays.deepEquals(new ColorWrapper[][] { {ColorWrapper.BLUE, ColorWrapper.CYAN}, {
            ColorWrapper.WHITE, ColorWrapper.BLACK}, {ColorWrapper.ORANGE, ColorWrapper.YELLOW} },
                MatrixMultiplier.applyScalarFilter(imageWrapper, new FlipFilter(imageWrapper.getWidth())).getData()));
    }
}