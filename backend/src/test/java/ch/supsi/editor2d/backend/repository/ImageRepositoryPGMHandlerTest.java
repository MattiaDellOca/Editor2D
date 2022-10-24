package ch.supsi.editor2d.backend.repository;

import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.exception.FileWritingException;
import ch.supsi.editor2d.backend.helper.ColorInterpolation;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImagePGM;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ImageRepositoryPGMHandlerTest {

    private ImageRepositoryPGMHandler imageRepositoryPGMHandler;
    private ImageRepositoryPBMHandler imageRepositoryPBMHandler;
    private final String pathImageTestPGMOk = Objects.requireNonNull(getClass().getClassLoader().getResource("PGM/testPGMOk.pgm")).getPath();
    private final String pathImageTestPGMWrongMagicNumber = Objects.requireNonNull(getClass().getClassLoader().getResource("PGM/testPGMWrongMagicNumber.pgm")).getPath();
    private final String pathImageTestPGMMalformedBody = Objects.requireNonNull(getClass().getClassLoader().getResource("PGM/testPGMMalformedBody.pgm")).getPath();

    @BeforeEach
    void init(){
        imageRepositoryPGMHandler = new ImageRepositoryPGMHandler();
        imageRepositoryPBMHandler = new ImageRepositoryPBMHandler();
    }

    @Test
    void handleLoadResultCorrect() {
        //result desired creation
        int widthDesiredResult = 10;
        int heightDesiredResult = 6;
        int scaleOfGray = 15;

        float grayColorValue1 = ColorInterpolation.interpolateRGBtoFloat(255 / scaleOfGray);
        float grayColorValue5 = ColorInterpolation.interpolateRGBtoFloat((255 / scaleOfGray) * 5);
        float grayColorValue10 = ColorInterpolation.interpolateRGBtoFloat((255 / scaleOfGray) * 10);

        ColorWrapper[][] data = new ColorWrapper[heightDesiredResult][widthDesiredResult];
        for (int h = 0; h < heightDesiredResult; h++) {
            for (int w = 0; w < widthDesiredResult; w++) {
                if (h == 0) {
                    data[h][w] = new ColorWrapper(grayColorValue1,grayColorValue1,grayColorValue1);
                } else if(h == 2){
                    data[h][w] = new ColorWrapper(grayColorValue5,grayColorValue5,grayColorValue5);
                }else if(h == 4){
                    data[h][w] = new ColorWrapper(grayColorValue10,grayColorValue10,grayColorValue10);
                }
                else {
                    data[h][w] = new ColorWrapper(0.f,0.f,0.f);
                }
            }
        }

        try {
            ImageWrapper obtainedResult = imageRepositoryPGMHandler.handleLoad("PGM", pathImageTestPGMOk);
            assertEquals(obtainedResult.getWidth(), widthDesiredResult);
            assertEquals(obtainedResult.getHeight(), heightDesiredResult);
            assertEquals(((ImagePGM) obtainedResult).getScaleGray(), scaleOfGray);
            for (int h = 0; h < heightDesiredResult; h++) {
                for (int w = 0; w < widthDesiredResult; w++) {
                    assertEquals(obtainedResult.getData()[h][w].getRed(), data[h][w].getRed());
                    assertEquals(obtainedResult.getData()[h][w].getGreen(), data[h][w].getGreen());
                    assertEquals(obtainedResult.getData()[h][w].getBlue(), data[h][w].getBlue());
                }
            }
        } catch (FileReadingException e) {
            fail();
        }
    }

    @Test
    void handleLoadWrongMagicNumber(){
        Exception ex = assertThrows(FileReadingException.class, () -> imageRepositoryPGMHandler.handleLoad("PGM",pathImageTestPGMWrongMagicNumber));
        assertEquals("Magic number is incorrect",ex.getMessage());
    }

    @Test
    void handleLoadMalformedBody(){
        Exception ex = assertThrows(FileReadingException.class, () -> imageRepositoryPGMHandler.handleLoad("PGM",pathImageTestPGMMalformedBody));
        assertEquals("Error during image loading",ex.getMessage());
    }

    /**
     * WARNING: This test depends on handleLoadResultCorrect test
     */
    @Test
    void handleSaveResultCorrect () throws FileReadingException, FileWritingException {
        // Check if the image is saved correctly

        // Load image + Save + Reload to check file structure correctness
        ImageWrapper pgmImage = imageRepositoryPGMHandler.handleLoad("PGM", pathImageTestPGMOk);

        final String exportDir = Paths.get(pathImageTestPGMOk).getParent().toString();
        final String exportedPath = Paths.get(exportDir, "testPBMOk.pbm").toString();
        imageRepositoryPBMHandler.handleSave(
                "pbm",
                "testPBMOk",
                exportDir,
                pgmImage
        );

        ImageWrapper obtainedResult = imageRepositoryPBMHandler.handleLoad("PBM", exportedPath);
        final List<ColorWrapper> data = new LinkedList<>();
        // Build a 10x6 image with a white line on line number 4
        for (int h = 0; h < pgmImage.getHeight(); h++) {
            for (int w = 0; w < pgmImage.getWidth(); w++) {
                if (h == 4) {
                    data.add(new ColorWrapper(1.f, 1.f, 1.f));
                } else {
                    data.add(new ColorWrapper(0.f, 0.f, 0.f));
                }
            }
        }

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
