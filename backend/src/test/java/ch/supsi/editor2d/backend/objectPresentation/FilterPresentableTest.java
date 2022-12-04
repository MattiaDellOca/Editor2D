package ch.supsi.editor2d.backend.objectPresentation;

import ch.supsi.editor2d.backend.model.filter.FlipFilter;
import ch.supsi.editor2d.backend.model.filter.GrayscaleFilter;
import ch.supsi.editor2d.backend.model.filter.SepiaFilter;
import ch.supsi.editor2d.backend.model.filter.SharpenFilter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilterPresentableTest {

    @Test
    void present() {
        FilterPresentable filterPresentable = new FilterPresentable();
        FlipFilter flipFilter = new FlipFilter();
        GrayscaleFilter grayscaleFilter = new GrayscaleFilter();
        SepiaFilter sepiaFilter = new SepiaFilter();
        SharpenFilter sharpenFilter = new SharpenFilter();

        assertEquals("Flip", filterPresentable.present(flipFilter));
        assertEquals("Grayscale", filterPresentable.present(grayscaleFilter));
        assertEquals("Sepia", filterPresentable.present(sepiaFilter));
        assertEquals("Sharpen", filterPresentable.present(sharpenFilter));
    }
}