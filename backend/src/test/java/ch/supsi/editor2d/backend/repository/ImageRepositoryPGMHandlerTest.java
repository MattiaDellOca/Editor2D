package ch.supsi.editor2d.backend.repository;

import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.exception.FileWritingException;
import ch.supsi.editor2d.backend.helper.ColorInterpolation;
import ch.supsi.editor2d.backend.model.ColorTest;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImagePGM;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ImageRepositoryPGMHandlerTest {

    private ImageRepositoryPGMHandler imageRepositoryPGMHandler;
    private final String pathImageTestPGMOk = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("PGM/testPGMOk.pgm")).getFile()).getAbsolutePath();
    private final String pathImageTestPGMWrongMagicNumber = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("PGM/testPGMWrongMagicNumber.pgm")).getFile()).getAbsolutePath();
    private final String pathImageTestPGMMalformedBody = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("PGM/testPGMMalformedBody.pgm")).getFile()).getAbsolutePath();

    @BeforeEach
    void init(){
        imageRepositoryPGMHandler = new ImageRepositoryPGMHandler();
    }

    @Test
    void handleLoadResultCorrect() {
        //result desired creation
        int widthDesiredResult = 10;
        int heightDesiredResult = 6;
        int scaleOfGray = 255;

        float grayColorValue1 = 0.06666667f;
        float grayColorValue5 = 0.32941177f;
        float grayColorValue10 = 0.66666667f;

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
                    assertEquals(obtainedResult.getData()[h][w].getRed(), data[h][w].getRed(), 0.05f);
                    assertEquals(obtainedResult.getData()[h][w].getGreen(), data[h][w].getGreen(), 0.05f);
                    assertEquals(obtainedResult.getData()[h][w].getBlue(), data[h][w].getBlue(), 0.05f);
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
        final String filename = "testPGMOk";
        final String extension = "pgm";
        final String exportedPath = Paths.get(exportDir, filename+"."+extension).toString();
        imageRepositoryPGMHandler.handleSave(
                extension,
                filename,
                exportDir,
                pgmImage
        );


        ImageWrapper obtainedResult = imageRepositoryPGMHandler.handleLoad("PGM", exportedPath);

        List<ColorWrapper> expected = new ArrayList<>();
        for(int i = 0; i < 10; i ++)
            expected.add(new ColorWrapper(0.06666667f, 0.06666667f, 0.06666667f));
        for(int i = 0; i < 10; i ++)
            expected.add(ColorTest.BLACK);
        for(int i = 0; i < 10; i ++)
            expected.add(new ColorWrapper(0.32941177f, 0.32941177f, 0.32941177f));
        for(int i = 0; i < 10; i ++)
            expected.add(ColorTest.BLACK);
        for(int i = 0; i < 10; i ++)
            expected.add(new ColorWrapper(0.6627451f, 0.6627451f, 0.6627451f));
        for(int i = 0; i < 10; i ++)
            expected.add(ColorTest.BLACK);

        assertEquals(obtainedResult.getWidth(), pgmImage.getWidth());
        assertEquals(obtainedResult.getHeight(), pgmImage.getHeight());

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
