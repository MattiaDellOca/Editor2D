package ch.supsi.editor2d.backend.repository;

import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.exception.FileWritingException;
import ch.supsi.editor2d.backend.model.ColorTest;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImagePPM;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ImageRepositoryPPMHandlerTest {

    private ImageRepositoryPPMHandler imageRepositoryPPMHandler;
    private final String pathImageTestPPMOk = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("PPM/testPPMOk.ppm")).getFile()).getAbsolutePath();
    private final String pathImageTestPPMWrongMagicNumber = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("PPM/testPPMWrongMagicNumber.ppm")).getFile()).getAbsolutePath();
    private final String pathImageTestPPMMalformedBody = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("PPM/testPPMMalformedBody.ppm")).getFile()).getAbsolutePath();

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
                {ColorTest.RED, ColorTest.GREEN, ColorTest.BLUE },
                {ColorTest.YELLOW, ColorTest.WHITE, ColorTest.BLACK },
                {ColorTest.CYAN, new ColorWrapper(75, 75, 75), new ColorWrapper(127, 127, 127) },
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

    /**
     * WARNING: This test depends on handleLoadResultCorrect test
     */
    @Test
    void handleSaveResultCorrect () throws FileReadingException, FileWritingException {
        // Check if the image is saved correctly

        // Load image + Save + Reload to check file structure correctness
        ImageWrapper ppmImage = imageRepositoryPPMHandler.handleLoad("PPM", pathImageTestPPMOk);

        final String exportDir = Paths.get(pathImageTestPPMOk).getParent().toString();
        final String filename = "testPPMOk";
        final String extension = "ppm";
        final String exportedPath = Paths.get(exportDir, filename+"."+extension).toString();
        imageRepositoryPPMHandler.handleSave(
                extension,
                filename,
                exportDir,
                ppmImage
        );


        ImageWrapper obtainedResult = imageRepositoryPPMHandler.handleLoad("PPM", exportedPath);

        List<ColorWrapper> expected = List.of(ColorTest.RED, ColorTest.GREEN, ColorTest.BLUE ,
                ColorTest.YELLOW, ColorTest.WHITE, ColorTest.BLACK,
                ColorTest.CYAN, new ColorWrapper(75, 75, 75), new ColorWrapper(127, 127, 127),
                new ColorWrapper(150, 150, 150), new ColorWrapper(150, 150, 150), new ColorWrapper(150, 150, 150));

        assertEquals(obtainedResult.getWidth(), ppmImage.getWidth());
        assertEquals(obtainedResult.getHeight(), ppmImage.getHeight());

        // compare each pixel using parallel stream to speed up the process
        final Iterator<ColorWrapper> it = expected.iterator();

        // Iterate image data and compare each pixel with the expected one
        Arrays.stream(obtainedResult.getData())
                .flatMap(Arrays::stream)
                .forEach(colorWrapper -> {
                    ColorWrapper expectedPixel = it.next();
                    assertEquals(expectedPixel.getRed(), colorWrapper.getRed(), 0.05f);
                    assertEquals(expectedPixel.getGreen(), colorWrapper.getGreen(), 0.05f);
                    assertEquals(expectedPixel.getBlue(), colorWrapper.getBlue(), 0.05f);
                });
    }
}