package ch.supsi.editor2d.backend.repository;

import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.exception.FileWritingException;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImagePPM;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ImageRepositoryPPMHandlerTest {

    private ImageRepositoryPPMHandler imageRepositoryPPMHandler;
    private ImageRepositoryPBMHandler imageRepositoryPBMHandler;
    private final String pathImageTestPPMOk = Objects.requireNonNull(getClass().getClassLoader().getResource("PPM/testPPMOk.ppm")).getPath();
    private final String pathImageTestPPMWrongMagicNumber = Objects.requireNonNull(getClass().getClassLoader().getResource("PPM/testPPMWrongMagicNumber.ppm")).getPath();
    private final String pathImageTestPPMMalformedBody = Objects.requireNonNull(getClass().getClassLoader().getResource("PPM/testPPMMalformedBody.ppm")).getPath();

    @BeforeEach
    void init(){
        imageRepositoryPPMHandler = new ImageRepositoryPPMHandler();
        imageRepositoryPBMHandler = new ImageRepositoryPBMHandler();
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

    /**
     * WARNING: This test depends on handleLoadResultCorrect test
     */
    @Test
    void handleSaveResultCorrect () throws FileReadingException, FileWritingException {
        // Check if the image is saved correctly

        // Load image + Save + Reload to check file structure correctness
        ImageWrapper pgmImage = imageRepositoryPPMHandler.handleLoad("PPM", pathImageTestPPMOk);

        final String exportDir = Paths.get(pathImageTestPPMOk).getParent().toString();
        final String filename = "testPPMOk";
        final String extension = "pbm";
        final String exportedPath = Paths.get(exportDir, filename+"."+extension).toString();
        imageRepositoryPBMHandler.handleSave(
                extension,
                filename,
                exportDir,
                pgmImage
        );


        ImageWrapper obtainedResult = imageRepositoryPBMHandler.handleLoad("PBM", exportedPath);
        final ColorWrapper WHITE = new ColorWrapper(1.f, 1.f, 1.f);
        final ColorWrapper BLACK = new ColorWrapper(0.f, 0.f, 0.f);

        final List<ColorWrapper> data = new LinkedList<>(Arrays.asList(
            BLACK, BLACK, BLACK,
            WHITE, WHITE, BLACK,
            WHITE, BLACK, BLACK,
            WHITE, WHITE, WHITE
        ));

        assertEquals(obtainedResult.getWidth(), pgmImage.getWidth());
        assertEquals(obtainedResult.getHeight(), pgmImage.getHeight());

        // compare each pixel using parallel stream to speed up the process
        final Iterator<ColorWrapper> it = data.iterator();

        // Iterate image data and compare each pixel with the expected one
        Arrays.stream(obtainedResult.getData())
                .flatMap(Arrays::stream)
                .forEach(colorWrapper -> {
                    ColorWrapper expected = it.next();
                    assertEquals(expected.getRed(), colorWrapper.getRed());
                    assertEquals(expected.getGreen(), colorWrapper.getGreen());
                    assertEquals(expected.getBlue(), colorWrapper.getBlue());
                });
    }
}