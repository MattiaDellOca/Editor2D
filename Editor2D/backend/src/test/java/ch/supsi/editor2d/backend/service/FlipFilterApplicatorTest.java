package ch.supsi.editor2d.backend.service;

import ch.supsi.editor2d.backend.model.FlipFilter;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FlipFilterApplicatorTest {

    @Test
    void apply() {
        Applicable flipFilterApplicator = new FlipFilterApplicator();
        ImageWrapper imageWrapper = new ImageWrapper(2, 3, new Color[][] { {Color.BEIGE, Color.ANTIQUEWHITE}, {Color.BLACK, Color.ALICEBLUE}, {Color.AQUA, Color.AQUAMARINE}});
        ImageWrapper result = new ImageWrapper(2, 3, new Color[][] { {Color.ANTIQUEWHITE, Color.BEIGE}, {Color.ALICEBLUE, Color.BLACK}, {Color.AQUAMARINE, Color.AQUA} });
        assertTrue(Arrays.deepEquals(result.getData(), flipFilterApplicator.apply(imageWrapper).getData()));
    }
}