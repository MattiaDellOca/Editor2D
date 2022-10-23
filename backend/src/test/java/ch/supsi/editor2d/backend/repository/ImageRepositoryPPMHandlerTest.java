package ch.supsi.editor2d.backend.repository;

import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImagePPM;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ImageRepositoryPPMHandlerTest {

    private ImageRepositoryPPMHandler imageRepositoryPPMHandler;
    private final String pathImageTestPPMOk = Objects.requireNonNull(getClass().getClassLoader().getResource("PPM/testPPMOk.ppm")).getPath();
    private final String pathImageTestPPMWrongMagicNumber = Objects.requireNonNull(getClass().getClassLoader().getResource("PPM/testPPMWrongMagicNumber.ppm")).getPath();
    private final String pathImageTestPPMMalformedBody = Objects.requireNonNull(getClass().getClassLoader().getResource("PPM/testPPMMalformedBody.ppm")).getPath();

    @BeforeEach
    void init(){
        imageRepositoryPPMHandler = new ImageRepositoryPPMHandler();
    }


    @Test
    void handleLoadResultCorrect() {

        // expected results creation
        int expectedWidth = 3;
        int expectedHeight = 4;
        int expectedRGBScale = 255;


        ColorWrapper[][] data = new ColorWrapper [][]{
                { ColorWrapper.RED, ColorWrapper.GREEN, ColorWrapper.BLUE },
                { ColorWrapper.YELLOW, ColorWrapper.WHITE, ColorWrapper.BLACK },
                { ColorWrapper.CYAN, new ColorWrapper(75, 75, 75), new ColorWrapper(127, 127, 127) },
                { new ColorWrapper(150, 150, 150), new ColorWrapper(150, 150, 150), new ColorWrapper(150, 150, 150) }
        };

        try {
            ImageWrapper actualImage = imageRepositoryPPMHandler.handleLoad("PPM", pathImageTestPPMOk);
            assertEquals(actualImage.getWidth(), expectedWidth);
            assertEquals(actualImage.getHeight(), expectedHeight);
            assertEquals(((ImagePPM) actualImage).getRGBScale(), expectedRGBScale);

            for (int h = 0; h < expectedHeight; h++) {
                for (int w = 0; w < expectedWidth; w++) {
                    assertArrayEquals(actualImage.getData()[h][w].getRGB(), data[h][w].getRGB(), 0.05f);
                }
            }

        } catch (FileReadingException e) {
            fail();
        }
    }

    @Test
    void handleLoadWrongMagicNumber(){
        Exception ex = assertThrows(FileReadingException.class, () -> imageRepositoryPPMHandler.handleLoad("PPM", pathImageTestPPMWrongMagicNumber));
        assertEquals("Magic number is incorrect",ex.getMessage());
    }

    @Test
    void handleLoadMalformedBody(){
        Exception ex = assertThrows(FileReadingException.class, () -> imageRepositoryPPMHandler.handleLoad("PPM", pathImageTestPPMMalformedBody));
        assertEquals("Error during image loading",ex.getMessage());

    }
}