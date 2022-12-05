package ch.supsi.editor2d.backend.model.filter;

import ch.supsi.editor2d.backend.exception.FilterApplyException;
import ch.supsi.editor2d.backend.helper.MatrixMultiplier;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SharpenFilterTest {

    @Test
     void applyCorrect() {
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