package ch.supsi.editor2d.backend.repository;

import ch.supsi.editor2d.backend.exception.FileReadingException;
import ch.supsi.editor2d.backend.exception.FileWritingException;
import ch.supsi.editor2d.backend.model.ColorWrapper;
import ch.supsi.editor2d.backend.model.ImageWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ImageRepositoryPBMHandlerTest {

    private ImageRepositoryPBMHandler imageRepositoryPBMHandler;
    private final String pathImageTestPBMOk = Objects.requireNonNull(getClass().getClassLoader().getResource("PBM/testPBMOk.pbm")).getPath();
    private final String pathImageTestPBMWrongMagicNumber = Objects.requireNonNull(getClass().getClassLoader().getResource("PBM/testPBMWrongMagicNumber.pbm")).getPath();
    private final String pathImageTestPBMMalformedBody = Objects.requireNonNull(getClass().getClassLoader().getResource("PBM/testPBMMalformedBody.pbm")).getPath();

    @BeforeEach
    void init(){
        imageRepositoryPBMHandler = new ImageRepositoryPBMHandler();
    }

    @Test
    void handleLoadResultCorrect() {
        //result desired creation
        int widthDesiredResult = 10;
        int heightDesiredResult = 6;
        ColorWrapper[][] data = new ColorWrapper[heightDesiredResult][widthDesiredResult];
        for (int h = 0; h < heightDesiredResult; h++) {
            for (int w = 0; w < widthDesiredResult; w++) {
                if (h % 2 == 0) {
                    data[h][w] = new ColorWrapper(0.f,0.f,0.f); //black
                } else {
                    data[h][w] = new ColorWrapper(1.f,1.f,1.f); //white;
                }
            }
        }

        try {
            ImageWrapper obtainedResult = imageRepositoryPBMHandler.handleLoad("PBM", pathImageTestPBMOk);
            assertEquals(obtainedResult.getWidth(), widthDesiredResult);
            assertEquals(obtainedResult.getHeight(), heightDesiredResult);
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
        Exception ex = assertThrows(FileReadingException.class, () -> imageRepositoryPBMHandler.handleLoad("PBM", pathImageTestPBMWrongMagicNumber));
        assertEquals("Magic number is incorrect",ex.getMessage());
    }

    @Test
    void handleLoadMalformedBody(){
        Exception ex = assertThrows(FileReadingException.class, () -> imageRepositoryPBMHandler.handleLoad("PBM", pathImageTestPBMMalformedBody));
        assertEquals("Error during image loading",ex.getMessage());
    }

    /**
     * WARNING: This test depends on handleLoadResultCorrect test
     */
    @Test
    void handleSaveResultCorrect() throws FileReadingException, FileWritingException {
        // Check if the image is saved correctly

        // Load image + Save + Reload to check file structure correctness
        ImageWrapper pgmImage = imageRepositoryPBMHandler.handleLoad("PBM", pathImageTestPBMOk);

        final String exportDir = Paths.get(pathImageTestPBMOk).getParent().toString();
        final String filename = "testPBMOk";
        final String extension = "pbm";
        final String exportedPath = Paths.get(exportDir, filename+"."+extension).toString();

        imageRepositoryPBMHandler.handleSave(
                extension,
                filename,
                exportDir,
                pgmImage
        );

        ImageWrapper obtainedResult = imageRepositoryPBMHandler.handleLoad("PBM", exportedPath);
        final List<ColorWrapper> data = new LinkedList<>();
        // Build a 10x6 image with a white line on line number 4
        for (int h = 0; h < pgmImage.getHeight(); h++) {
            for (int w = 0; w < pgmImage.getWidth(); w++) {
                if (h%2 == 0) {
                    data.add(new ColorWrapper(0.f, 0.f, 0.f));
                } else {
                    data.add(new ColorWrapper(1.f, 1.f, 1.f));
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
